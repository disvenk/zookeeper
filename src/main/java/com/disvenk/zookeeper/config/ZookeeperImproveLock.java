//package com.disvenk.zookeeper.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.I0Itec.zkclient.IZkChildListener;
//import org.I0Itec.zkclient.IZkDataListener;
//import org.I0Itec.zkclient.ZkClient;
//import org.I0Itec.zkclient.serialize.SerializableSerializer;
//import org.springframework.stereotype.Component;
//import sun.util.resources.cldr.lag.LocaleNames_lag;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//
//@Slf4j
//public class ZookeeperImproveLock implements Lock {
//
//    private static final String LOCK_PATH  = "/LOCK";
//
//    private static final String ZOOKEEPER_ID_PORT="localhost:2181";
//
//    private ZkClient client = new ZkClient(ZOOKEEPER_ID_PORT,30000,1000,new SerializableSerializer());
//
//    private CountDownLatch cdl;
//
//    private String beforePath;//当前请求节点
//
//    private String currentPath;//当前请求节点的前一个节点
//
//    //判断有没有lock目录，没有则创建
//    public ZookeeperImproveLock(){
//        if(!this.client.exists(LOCK_PATH)){
//            this.client.createPersistent(LOCK_PATH);
//        }
//    }
//
//    public boolean tryLock(){
//
//        //如果currentPath为空为第一次尝试加锁，第一次加锁赋值currentPath；
//        if(currentPath==null || currentPath.length()<=0){
//            //创建一个临时顺序节点
//            currentPath = this.client.createEphemeralSequential(LOCK_PATH+"/","lock");
//            log.info("------------->"+currentPath);
//        }
//        System.out.println("当前保存的目录"+currentPath);
//        //获得所有临时节点并排序，临时节点名称为自增长的字符串，如：00000000040
//        List<String> childrens = this.client.getChildren(LOCK_PATH);
//        Collections.sort(childrens);
//
//        if(currentPath.equals(LOCK_PATH+"/"+childrens.get(0))){
//            //如果当前节点在所有节点中排名第一则获取锁成功
//            return true;
//        }else {
//            //如果当前节点在所有节点中排名不是第一，则获取前面节点名称，并赋值给beforePath
//            int wz = Collections.binarySearch(childrens,currentPath.substring(6));
//            beforePath = LOCK_PATH+"/"+childrens.get(wz-1);
//        }
//        return false;
//    }
//
//    private void waitForLock(){
//        IZkDataListener listener = new IZkDataListener() {
//
//            @Override
//            public void handleDataDeleted(String dataPath) throws Exception{
//                log.info(Thread.currentThread().getName()+":捕获到DataDelete事件！------");
//                if(cdl!=null){
//                    cdl.countDown();
//                }
//            }
//
//            @Override
//            public void handleDataChange(String dataPath, Object data) throws Exception {
//            }
//        };
//        //给排在前面的节点增加数据删除的watcher
//        this.client.subscribeDataChanges(beforePath,listener);
//        if(this.client.exists(beforePath)){
//            cdl = new CountDownLatch(1);
//            try{
//                cdl.await();
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//        this.client.unsubscribeDataChanges(beforePath,listener);
//    }
//
//    @Override
//    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
//        return false;
//    }
//
//    @Override
//    public void unlock() {
//
//    }
//
//    @Override
//    public Condition newCondition() {
//        return null;
//    }
//
//    public void unLock(){
//        //删除当前临时节点
//        client.delete(currentPath);
//    }
//
//    public void lock(){
//        if(!tryLock()){
//            waitForLock();
//            lock();
//        }else {
//            log.info(Thread.currentThread().getName()+"获得分布式锁");
//        }
//    }
//
//    @Override
//    public void lockInterruptibly() throws InterruptedException {
//
//    }
//}
