package com.sccl.framework.sent2flex;

public class Send2Flex {
	
	MessageBrokFactory messageBrokFactory;
	
	public void controlThread(String str) {
		if(str.equals("start")) {
			try {
				messageBrokFactory = new MessageBrokFactory();
				messageBrokFactory.start();
			} catch (Exception e) {
				 e.printStackTrace();
			}
		} else {
			messageBrokFactory.stop();
		}
	}
}
