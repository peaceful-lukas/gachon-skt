package com.lukaslab.proj.skt;

import com.lukaslab.proj.skt.config.Config;
import com.lukaslab.proj.skt.database.DatabaseManager;
import com.lukaslab.proj.skt.database.OracleDatabaseManager;
import com.lukaslab.proj.skt.job.Job;
import com.lukaslab.proj.skt.job.MainJob;
import com.lukaslab.proj.skt.logger.Logger;


/**
 * Hello world!
 *
 */
public class App {
	
	// TODO : 전체 프로젝트 마무리 하기 위해 남은 작업
	// 1. 현재 버전에서는 Oracle DB 에서 읽어들인 tuple 들에 대한 mark 가 없다. (어디까지 읽어왔는지 모르고 읽어오게됨.)
	// 2. DB 커넥션 풀링
	// 3. AsyncTaskManager 제네릭하게 구현하기
	// 4. defensive 코딩
	
    
    public void init() {

    	//----- set Logger level. ( DEBUG || INFO || WARN || ERROR )
    	Logger.setLevel(Logger.LEVEL_DEBUG);
    	
    	//----- initialize Config object.
    	Config.init();
    	
    	//----- initialize database connection.
    	DatabaseManager dbManager = OracleDatabaseManager.getInstance();
    	dbManager.init();
    }
    
    public void start() {
//    	while(true) {
    		Job job = new MainJob();
    		job.start();
    		Logger.debugln("-------------------- finish a job.");
//    	}
    }
    
}
