/*
	file name: TocHW4.java
	name: �L�ʫ�
	student id: F74002159
	description:
		class 'Road'�ΨӬ����C�������S�ʡA�]�t���W�B�̤j�̤p�������B�����X�{�������M�䦸�ơA�̭����F�U��variable��get�Bset function�~�A�٦�'Road()'�ΨӪ��l�ơA
		�@�}�l���̤j�������]��0�B�̤p�������g�{30000000�A'setRoad'�Φb�Ĥ@���O���A�h�F���W�o�ӰѼơA'addMonth'�N�ΨӰO�����X�{�L�����q�A���F�����������������~�A�٭n�O���������B
		�A��'changePrice'�Ӹ��̤j�̤p�Ȱ������M�ܰʡC
		'main' �̡A�@�}�l�qprogram��1�ӰѼ�Ū��url�A�U�����ƨ�data.json�A�����AŪ�ҩ��J�@json array�A�������W�ϧ_�����L�A�o�̸��W�]�AXX���BXX�ѩMXX���A����json array
		�O�������A�A���X���������̦h���������q�A�L�X���W�M�̤j�̤p�����q�C
*/
import org.json.*;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.String;

public class TocHw4 {
	public static class Road{
		private String roadName;
		private int maxPrice;
		private int minPrice;
		private int month[] = new int[200];		// the month that there are houses sold in
		private int monthNum;				// the num of the month that there is a house on this road sold 
		//private boolean maxMonNum = false;	// if it has the max num the month that there is a house on this road sold,
												// it turns true
		public Road() {
			setName(null);
			setMax(0);
			setMin(30000000);
			for(int i = 0; i<200; i++) {
				setMon(i, monthNum);
			}
			setMonNum(0);
		}
		
		/* the road first recorded */
		public void setRoad(String name, int price, int month) {
			setName(name);
			changePrice(price);
			setMon(month, monthNum);
			addMonNum();
		}
		
		/* if the road has been recorded */
		public void addMonth(int price, int month) {
			changePrice(price);
			boolean tmp = true;
			for(int i = 0; i<120; i++) {
				if(getMon(i) == month)
					tmp = false;
			}
			if(tmp) {
				setMon(month, monthNum);
				addMonNum();
			}
		}
		
		public void setName(String s) {
			roadName = s;
		}
		
		public String getName() {
			return roadName;
		}
		
		public void setMax(int n) {
			maxPrice = n;
		}
		
		public int getMax() {
			return maxPrice;
		}
		
		public void setMin(int n) {
			minPrice = n;
		}
		
		public int getMin() {
			return minPrice;
		}
		
		public void addMonNum() {
			monthNum++;
		}
		
		public void setMonNum(int n) {
			monthNum = n;
		}
		
		public int getMonNum() {
			return monthNum;
		}
		
		public void setMon(int m, int num) {
			month[num] = m;
		}
		
		public int getMon(int n) {
			return month[n];
		}
		
		/*
		public void setMaxMonNum() {
			if(maxMonNum == false)
				maxMonNum = true;
			else
				maxMonNum = false;
		}
		
		public boolean getMaxMonNum(int n) {
			return maxMonNum;
		}
		*/
		
		public void changePrice(int n) {
			if(n>getMax()) 
				setMax(n);
			else if(n<getMin())
				setMin(n);
		}

		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, JSONException
	{
		final int buf_max = 100000;
		
			//long time_begin, time_end;
			//time_begin = System.currentTimeMillis();
			
			/* reading url and turn it into "json.json" */
			//String url="http://www.datagarage.io/api/5365dee31bc6e9d9463a0057";
			String url = args[0];
	        
	        //System.out.println("reading by url ...");
	        try {
	            URL url_address = new URL( url );
	            
	            // Ū�J����(�r�����y)            
	            BufferedReader br = new BufferedReader(new InputStreamReader(url_address.openStream(), "UTF-8"));
	            //BufferedReader br = new BufferedReader(new InputStreamReader(url_address.openStream(), "Big5"));
	            BufferedWriter bw = new BufferedWriter(new FileWriter("data.json", false));    
	            String oneLine = null ;
	            
	            while ((oneLine = br.readLine()) != null) {
	                bw.write(oneLine);                
	            }
	            bw.close();
	            br.close();
	            
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        //System.out.println("Done");
	        
	        /* read json file to a josn array */
	        JSONArray jsonRealPrice = new JSONArray(new JSONTokener(new FileReader(new File("data.json"))));
	        Road[] road = new Road[buf_max];
	        for(int i=0; i<buf_max; i++) {
	        	road[i] = new Road();
	        }
	        Pattern p = Pattern.compile(".*[��|��|��]");
	        Matcher matcher = null;
	        //Matcher matcher = p.matcher("�ڬO�n�n��22��");
	        //System.out.println("find: "+matcher.find());
	        //System.out.printf("result: %s\n", matcher.group(0));
	        
	        /* put the object to its road */
	        int roadCtr = 0;
	        for(int i=0; i<jsonRealPrice.length(); i++) {
	        	matcher = p.matcher(jsonRealPrice.getJSONObject(i).getString("�g�a�Ϭq���m�Ϋت��Ϫ��P"));
	        	if(matcher.find()) { //System.out.printf("Got one!!!\n");
	        		boolean newFlag = true;			// check if it's a new road
	        		for(int j=0; j<roadCtr+1; j++) {
	        			//System.out.printf("result: %s\n", road[j].getName());
	        			//System.out.printf("compare: %s;%s\n", road[j].getName(), matcher.group(0));
	        			
	        			
	        			//String matchName = matcher.group(0);
	        			if(matcher.group(0).equals(road[j].getName())) { 
	        				road[j].addMonth(jsonRealPrice.getJSONObject(i).getInt("�`����"), 
	        						jsonRealPrice.getJSONObject(i).getInt("�����~��"));
	        				//System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("�`����")+";"+
	        						//jsonRealPrice.getJSONObject(i).getInt("�����~��"));
	        				newFlag = false;
	        					
	        			}
	        			/*else if(j==(buf_max-1)) {
	        				System.out.printf("Got new road!!!\n");
	        				roadCtr++;
	        				road[roadCtr].setRoad(matcher.group(0), jsonRealPrice.getJSONObject(i).getInt("�`����"), 
	        						jsonRealPrice.getJSONObject(i).getInt("�����~��"));
	        				System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("�`����")+";"+
	        						jsonRealPrice.getJSONObject(i).getInt("�����~��"));
	        			}*/
	        				
	        		}
	        		if(newFlag) {
	        			//System.out.printf("Got new road!!!\n");
        				roadCtr++;
        				road[roadCtr].setRoad(matcher.group(0), jsonRealPrice.getJSONObject(i).getInt("�`����"), 
        						jsonRealPrice.getJSONObject(i).getInt("�����~��"));
        				//System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("�`����")+";"+
        				//		jsonRealPrice.getJSONObject(i).getInt("�����~��"));
	        		}
	        	}
	        	
	        }
	        
	        /* fin the max num of month of tradiotion of the road */
	        int maxNum = 0;
	        for(int i=0; i<buf_max; i++) {
	        	if(road[i].getMonNum()>maxNum) {
	        		maxNum = road[i].getMonNum();
	        	}
	        }
	        
	        for(int i=0; i<buf_max; i++) {
	        	if(road[i].getMonNum()==maxNum)
	        		System.out.printf("%s, �̰�������: %d, �̧C������: %d\n", road[i].getName(), road[i].getMax(), 
	    	        		road[i].getMin());
	        }
	        
	        
	        
	        
	}    
	        
}
