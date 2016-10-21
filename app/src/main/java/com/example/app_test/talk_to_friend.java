package com.example.app_test;

import java.util.ArrayList;
import java.util.List;

import ServerConnect.GetInfo;
import android.R.string;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class talk_to_friend extends Activity{
	private Bundle bundle;
	private String friend_name;

	
	private ListView msgListView;
	
	private EditText inputText;

	private Button send;
	
	private MsgAdapter adapter;
	private int helloworld = 0,helloworld_1 =0;
	private String username ;
	private GetInfo getinfo = new GetInfo();
	private List<Msg> msgList = new ArrayList<Msg>();
	private 	String content = null;
	private String result,message,message_send;
	private Handler myHandler ;
	private 	Thread out;
	private boolean STOPTHIS = true;
	private int getmessage = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//设置窗口没有标题栏
		
		setContentView(R.layout.chat_main);
		Intent intent  = this.getIntent();
		bundle = intent.getExtras();
		friend_name = bundle.getString("friend");
		username = bundle.getString("user");
		this.setTitle(friend_name);
		initMsg();
		adapter = new MsgAdapter(talk_to_friend.this, R.layout.chat_listview, msgList);
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				message_send = inputText.getText().toString();
				if(!"".equals(message_send)){
					
					Msg msg = new Msg(message_send, Msg.SENT);
					
					msgList.add(msg);
					adapter.notifyDataSetChanged();//有新消息时，刷新ListView中的显示
					msgListView.setSelection(msgList.size());//将ListView定位到最后一行
					inputText.setText("");//清空输入框的内容
					Message message = new Message();
					message.what = 2;

					myHandler.sendMessage(message);
				}
			}

			
			
		});
		
		Message message = new Message();
		message.what = 0;
		
		myHandler = new Handler() {  
	          public void handleMessage(Message msg) {   
	               switch (msg.what) {   
	                    case 0:   
	                    	getMessage();
	                         break;   
	                    case 1:
	                    	showmessage();
	                    	break;
	                    case 2:
	                    	SendMessage();
	                    	break;
	                    case 3:
	                    	System.out.println("do noting");
	                    	break;
	               }   
	               super.handleMessage(msg);   
	          }

			

			
	     };  
	     myHandler.sendMessage(message);
	     
		
	
	}
	public void showmessage(){
		if(!(content == null || content.equals(""))){
			Msg msg = new Msg(content, Msg.RECEIVED);
			msgList.add(msg);
			adapter.notifyDataSetChanged();//有新消息时，刷新ListView中的显示
			msgListView.setSelection(msgList.size());//将ListView定位到最后一行
		}
		Message message = new Message();
		message.what = 3;

		myHandler.sendMessage(message);
	}
	private void SendMessage() {
		// TODO Auto-generated method stub
		System.out.println("debug this is send message funciton !!!!");
		Thread thread = new Thread(){
			public void run(){
				
				Thread showmsg = new Thread(){
					public void run(){
						helloworld_1 = 0;
						message = getinfo.get_message(username, friend_name);
						if(message==null || message.equals("") || message.equals("false")  )
							result = getinfo.insert_message(username, friend_name, message_send);
						else
							result = getinfo.update_message(username, friend_name, message_send);
						System.out.println("debug send message: " + result);
						helloworld_1 = 1;
						
					}
				};
				showmsg.start();
				while (helloworld_1 == 0)
				{
					
				}
			}
		};
		thread.start();
		
			
	}
	private void getMessage(){
		
		System.out.println("this is get message");
	
		out = new Thread(){
					public void run(){
						while(STOPTHIS){
							 Thread thread = new Thread(){
									public void run(){
										
									
									Thread showmsg = new Thread(){
											public void run(){
												helloworld = 0;
												content=null;
												content = getinfo.get_message(friend_name, username);
												if(content==null || content.equals("") || content.equals("false") )
													System.out.println("no message");
												helloworld = 1;
														
											}
									};
									showmsg.start();
								
									while (helloworld == 0)
									{
													
									}
									if(content==null || content.equals("") || content.equals("false") || content.equals("0") )
										System.out.println("no message");
									else{
										Message message = new Message();
										message.what = 1;
										myHandler.sendMessage(message);
									}		
								}
							};
							if(getmessage == 0)
							{
								thread.start();
								getmessage = 1;
							}
							if(helloworld == 1)
								thread.start();
							try {
								currentThread().sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}																		
					}
				}
			};
		    out.start();
					
							
			
			
	}

	private void initMsg() {
		
		Msg msg1 = new Msg("You can talk to me NOW!",Msg.RECEIVED);
		msgList.add(msg1);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	public class Msg{
		
		public static final int RECEIVED = 0;//收到一条消息
		
		public static final int SENT = 1;//发出一条消息
		
		private String  content;//消息的内容
		
		private int type;//消息的类型
		
		public  Msg(String content,int type){
			this.content = content;
			this.type = type;
		}
		
		public String getContent(){
			return content;
		}
		
		public int getType(){
			return type;
		}
	}
	
	public class MsgAdapter extends ArrayAdapter<Msg>{
		private int resourceId;

		public MsgAdapter(Context context, int textViewresourceId, List<Msg> objects) {
			super(context, textViewresourceId, objects);
			resourceId = textViewresourceId;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Msg msg = getItem(position);
			View view;
			ViewHolder viewHolder;
			
			if(convertView == null){
				view = LayoutInflater.from(getContext()).inflate(resourceId, null);
				viewHolder = new ViewHolder();
				viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
				viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_Layout);
				viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
				viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);
				view.setTag(viewHolder);
			}else{
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			
			if(msg.getType()==Msg.RECEIVED){
				//如果是收到的消息，则显示左边消息布局，将右边消息布局隐藏
				viewHolder.leftLayout.setVisibility(View.VISIBLE);
				viewHolder.rightLayout.setVisibility(View.GONE);
				viewHolder.leftMsg.setText(msg.getContent());
			}else if(msg.getType()==Msg.SENT){
				//如果是发出去的消息，显示右边布局的消息布局，将左边的消息布局隐藏
				viewHolder.rightLayout.setVisibility(View.VISIBLE);
				viewHolder.leftLayout.setVisibility(View.GONE);
				viewHolder.rightMsg.setText(msg.getContent());
			}
			return view;
		}
		
		class ViewHolder{
			LinearLayout leftLayout;
			LinearLayout rightLayout;
			TextView leftMsg;
			TextView rightMsg;
		}
		
	}
	 public void onBackPressed() {  
         super.onBackPressed();  
         STOPTHIS = false;
         this.finish();        
     }
}
