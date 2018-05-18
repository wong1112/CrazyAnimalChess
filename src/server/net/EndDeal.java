package server.net;

import server.tool.HashMapManager;
import server.tool.MessageManager;

public class EndDeal {
	/**
	 * 结束处理
	 * */
	public void clientOff(int uid) {

		HashMapManager manager = HashMapManager.getInstance();

//		是否已配对
		if(manager.getMatchs().containsKey(uid)){

			int oppoId = HashMapManager.getInstance().getMatchs().get(uid);

//			是否已准备
			if(manager.getReadys().contains(uid)){
				manager.getReadys().remove(uid);
			}
			if(manager.getReadys().contains(oppoId)){
				manager.getReadys().remove(oppoId);
			}

//			是否已注册FightManager
			if(manager.getFightManagers().containsKey(uid)){


//				对手是否已注册FightManager
				if(manager.getFightManagers().containsKey(oppoId)){
//					移除FightManager
					manager.getFightManagers().remove(oppoId);
				}
//				移除FightManager
				manager.getFightManagers().remove(uid);
			}

//			移除配对
			manager.removeMatchs(uid);

		}



//		是否正在配对
		if(manager.getMatching().containsKey(uid)){
//			移除正在配对
			manager.getMatching().remove(uid);
		}

//		发送移除客户端指令
		new Action().removeClient(uid);
		MessageManager.getInstance().addMessage("玩家" + uid + "下线了！");


//		移除player
		manager.removePlayer(uid);

	}

}
