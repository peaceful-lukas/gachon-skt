package com.lukaslab.proj.skt.job;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.lukaslab.proj.skt.database.DataObject;

public class ExternalExecutionCallable implements Callable<String> {
	
	private DataObject dataObj;
	private int index;
	
	public ExternalExecutionCallable(DataObject dataObj, int index) {
		this.dataObj = dataObj;
		this.index = index;
	}
	
	public String call() {
		List<String> cmd = new ArrayList<String>();
		cmd.add("java");
		cmd.add("-cp"); 
		cmd.add("."); // . 표시는 별도의 classpath 없음.
		cmd.add("skt_external.ExternalJava"); // 실행할 class 파일명.
		cmd.add(String.valueOf(dataObj.getTxLoss()));
		cmd.add(String.valueOf(dataObj.getTxDmean()));
		cmd.add(String.valueOf(dataObj.getTxJmean()));
		cmd.add(String.valueOf(dataObj.getRxLoss()));
		cmd.add(String.valueOf(dataObj.getRxDmean()));
		cmd.add(String.valueOf(dataObj.getRxJmean()));
		cmd.add("/Users/lukas/Documents/workspace/skt/tmp/output_" + index+ ".txt");
		
		try {
			ProcessBuilder pb = new ProcessBuilder(cmd);
			pb.directory(new File("/Users/lukas/Documents/workspace/skt_external/bin"));
			pb.redirectErrorStream(true);
			Process proc = pb.start();
			
			Reader reader = new InputStreamReader(proc.getInputStream());
			int ch;
			while((ch = reader.read()) != -1) System.out.print((char)ch);
			reader.close();
			
			proc.waitFor();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		return "tmp/output_" + index + ".txt"; // 현재 프로젝트 디렉토리 구조상에서 파일 path 리턴.
	}

}
