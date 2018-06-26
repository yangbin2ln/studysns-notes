package com.insproject.provider.module.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;

/**
 * 笔记推送服务端
 * @author yangbin
 *
 */
@Service
public class NotesSend implements WebSocketHandler{
	  
	private  CopyOnWriteArraySet<WebSocketSession> userSocketSessionSet = new CopyOnWriteArraySet<WebSocketSession>();
	
      
    @Override  
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {  
        if (!userSocketSessionSet.contains(session)) {  
        	userSocketSessionSet.add(session);  
        }  
        for(int i=0;i<10;i++){  
            //broadcast(new TextMessage(new GsonBuilder().create().toJson("\"number\":\""+i+"\"")));
        	Map<String,Object> map = new HashMap<String,Object>();
        	map.put("nmber", i);
            session.sendMessage(new TextMessage(JSON.toJSONString(map)));  
        }  
    }  
  
    @Override  
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {  
        // TODO Auto-generated method stub  
        //Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);  
        //msg.setDate(new Date());  
//      sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));  
          
        session.sendMessage(message);  
    }  
  
    @Override  
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {  
        // TODO Auto-generated method stub  
        if (session.isOpen()) {  
            session.close();  
        }  
        Iterator<WebSocketSession> it = userSocketSessionSet.iterator();  
        // 移除Socket会话  
        while (it.hasNext()) {  
        	WebSocketSession entry = it.next();  
            if (entry.equals(session)) {  
            	userSocketSessionSet.remove(entry);  
                System.out.println("Socket会话已经移除:用户ID" + entry.getId());  
                break;  
            }  
        }  
    }  
  
    @Override  
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {  
        // TODO Auto-generated method stub  
        System.out.println("Websocket:" + session.getId() + "已经关闭");  
        Iterator<WebSocketSession> it = userSocketSessionSet.iterator();  
        // 移除Socket会话  
        while (it.hasNext()) {  
        	WebSocketSession entry = it.next();  
            if(entry.equals(session.getId())) {  
                userSocketSessionSet.remove(entry);  
                System.out.println("Socket会话已经移除:用户ID" + entry);  
                break;  
            }  
        }  
    }  
  
    @Override  
    public boolean supportsPartialMessages() {  
        // TODO Auto-generated method stub  
        return false;  
    }  
    
    public void broadcast(final TextMessage message) throws IOException {  
        Iterator<WebSocketSession> it = userSocketSessionSet.iterator();  
  
        // 多线程群发  
        while (it.hasNext()) {  
  
            final WebSocketSession entry = it.next();  
  
            if (entry.isOpen()) { 
            	entry.sendMessage(message);  
            }  
  
        }  
    }  
      
    /** 
     * 给所有在线用户的实时工程检测页面发送消息 
     *  
     * @param message 
     * @throws IOException 
     */  
    public void sendMessageToJsp(final TextMessage message,String type) throws IOException {  
    	Iterator<WebSocketSession> it = userSocketSessionSet.iterator();  
    }

	public CopyOnWriteArraySet<WebSocketSession> getUserSocketSessionSet() {
		return userSocketSessionSet;
	}

	public void setUserSocketSessionSet(CopyOnWriteArraySet<WebSocketSession> userSocketSessionSet) {
		this.userSocketSessionSet = userSocketSessionSet;
	}  
    
    public void sendMessage(String message) throws IOException{
    	 Iterator<WebSocketSession> it = userSocketSessionSet.iterator();
    	 while(it.hasNext()){
    		 WebSocketSession session = it.next();
    		 session.sendMessage(new TextMessage(message));
    	 }
    }
}
