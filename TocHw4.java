/*
	file name: TocHW4.java
	name: 林廷彥
	student id: F74002159
	description:
		class 'Road'用來紀錄每條路的特性，包含路名、最大最小交易價、交易出現的月份和其次數，裡面除了各個variable的get、set function外，還有'Road()'用來初始化，
		一開始的最大交易價設為0、最小交易價射程30000000，'setRoad'用在第一次記錄，多了路名這個參數，'addMonth'就用來記錄有出現過的路段，除了紀錄路的交易月份外，還要記錄交易金額
		，用'changePrice'來跟最大最小值做比較和變動。
		'main' 裡，一開始從program第1個參數讀取url，下載資料到data.json，之後再讀黨放入一json array，比較路名使否紀錄過，這裡路名包括XX路、XX大道和XX街，如果都沒有符合才對照XX巷，
		整個 json array 記錄完後，再找出交易月份最多月份的路段，印出路名和最大最小交易量。
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
	            
	            // 讀入網頁(字元串流)            
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
	        Pattern p = Pattern.compile(".*[路|街|大道]");
	        Matcher matcher = null;
	        //Matcher matcher = p.matcher("我是好好街22號");
	        //System.out.println("find: "+matcher.find());
	        //System.out.printf("result: %s\n", matcher.group(0));
	        
	        /* put the object to its road */
	        int roadCtr = 0;
	        for(int i=0; i<jsonRealPrice.length(); i++) {
	        	matcher = p.matcher(jsonRealPrice.getJSONObject(i).getString("土地區段位置或建物區門牌"));
	        	
	        	/* if it is a road */
	        	if(matcher.find()) { //System.out.printf("Got one!!!\n");
	        		boolean newFlag = true;			// check if it's a new road
	        		for(int j=0; j<roadCtr+1; j++) {
	        			//System.out.printf("result: %s\n", road[j].getName());
	        			//System.out.printf("compare: %s;%s\n", road[j].getName(), matcher.group(0));
	        			
	        			
	        			//String matchName = matcher.group(0);
	        			if(matcher.group(0).equals(road[j].getName())) { 
	        				road[j].addMonth(jsonRealPrice.getJSONObject(i).getInt("總價元"), 
	        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
	        				//System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("總價元")+";"+
	        						//jsonRealPrice.getJSONObject(i).getInt("交易年月"));
	        				newFlag = false;
	        					
	        			}
	        			/*else if(j==(buf_max-1)) {
	        				System.out.printf("Got new road!!!\n");
	        				roadCtr++;
	        				road[roadCtr].setRoad(matcher.group(0), jsonRealPrice.getJSONObject(i).getInt("總價元"), 
	        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
	        				System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("總價元")+";"+
	        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
	        			}*/
	        				
	        		}
	        		if(newFlag) {
	        			//System.out.printf("Got new road!!!\n");
        				roadCtr++;
        				road[roadCtr].setRoad(matcher.group(0), jsonRealPrice.getJSONObject(i).getInt("總價元"), 
        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
        				//System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("總價元")+";"+
        				//		jsonRealPrice.getJSONObject(i).getInt("交易年月"));
	        		}
	        	} //if(matcher.find)
	        	
	        	/* check if it is a alley */
	        	else {
	        		Pattern pp = Pattern.compile(".*巷");
	        		matcher = pp.matcher(jsonRealPrice.getJSONObject(i).getString("土地區段位置或建物區門牌"));
	        		if(matcher.find()) { //System.out.printf("Got one!!!\n");
		        		boolean newFlag = true;			// check if it's a new road
		        		for(int j=0; j<roadCtr+1; j++) {
		        			//System.out.printf("result: %s\n", road[j].getName());
		        			//System.out.printf("compare: %s;%s\n", road[j].getName(), matcher.group(0));
		        			
		        			
		        			//String matchName = matcher.group(0);
		        			if(matcher.group(0).equals(road[j].getName())) { 
		        				road[j].addMonth(jsonRealPrice.getJSONObject(i).getInt("總價元"), 
		        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
		        				//System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("總價元")+";"+
		        						//jsonRealPrice.getJSONObject(i).getInt("交易年月"));
		        				newFlag = false;
		        					
		        			}
		        			/*else if(j==(buf_max-1)) {
		        				System.out.printf("Got new road!!!\n");
		        				roadCtr++;
		        				road[roadCtr].setRoad(matcher.group(0), jsonRealPrice.getJSONObject(i).getInt("總價元"), 
		        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
		        				System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("總價元")+";"+
		        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
		        			}*/
		        				
		        		}
		        		if(newFlag) {
		        			//System.out.printf("Got new road!!!\n");
	        				roadCtr++;
	        				road[roadCtr].setRoad(matcher.group(0), jsonRealPrice.getJSONObject(i).getInt("總價元"), 
	        						jsonRealPrice.getJSONObject(i).getInt("交易年月"));
	        				//System.out.println(matcher.group(0)+";"+jsonRealPrice.getJSONObject(i).getInt("總價元")+";"+
	        				//		jsonRealPrice.getJSONObject(i).getInt("交易年月"));
		        		}
	        		} // if(matcher.find), find alley
	        	} // else
	        	
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
	        		System.out.printf("%s, 最高成交價: %d, 最低成交價: %d\n", road[i].getName(), road[i].getMax(), 
	    	        		road[i].getMin());
	        }
	        
	        
	        
	        
	}    
	        
}
