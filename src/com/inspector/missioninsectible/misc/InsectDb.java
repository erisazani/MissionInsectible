package com.inspector.missioninsectible.misc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 

public class InsectDb extends SQLiteOpenHelper {
	 private final int HS_INDEX = 5; 
	 static final String dbName = "insectDB";
     static final String tHiScore = "HiScore";
     static final String fHSId = "HiScoreNum";
     static final String fHSName = "PlayerName";
     static final String fHiScore = "HighScore";
     static final String tGallery = "Gallery";
     static final String fGId = "GalleryID";
     static final String fGLongestTime = "LongestName";
     static final String fGPlayedAmount = "PlayedAmount";
     static final String ins1 = "Beetle";
	 static final String ins2 = "Ladybug";
	 static final String ins3 = "Grasshopper";
	 static final String ins4 = "ButterFly";
	 static final String ins5 = "HoneyBee";
	 static final String ins6 = "GoldenDragonfly";
	 static final String ins7 = "TimeInsect";
    
     public InsectDb(Context context) {
	//THE VALUE OF 1 ON THE NEXT LINE REPRESENTS THE VERSION NUMBER OF THE DATABASE
	//IN THE FUTURE IF YOU MAKE CHANGES TO THE DATABASE, YOU NEED TO INCREMENT THIS NUMBER
	//DOING SO WILL CAUSE THE METHOD onUpgrade() TO AUTOMATICALLY GET TRIGGERED
             super(context, dbName, null, 1);
     }
     
     

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tHiScore+" (" +
                 fHSId + " INTEGER PRIMARY KEY , " +
                 fHSName + " TEXT, " +
                 fHiScore + " INTEGER " +
                 ")");
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS "+tGallery+" (" +
				 fGId + " INTEGER PRIMARY KEY , " +
				 fGLongestTime + " INTEGER, " +
				 fGPlayedAmount + " INTEGER, " +
                 ins1 + " INTEGER, " +
                 ins2 + " INTEGER, " +
                 ins3 + " INTEGER, " +
                 ins4 + " INTEGER, " +
                 ins5 + " INTEGER, " +
                 ins6 + " INTEGER, " +
                 ins7 + " INTEGER " +
                 ")");
		
		 ContentValues cv = new ContentValues();
         cv.put(fGId, 0);
         cv.put(fGLongestTime, "0");
         cv.put(fGPlayedAmount, "0");
         cv.put(ins1, "0");
         cv.put(ins2, "0");
         cv.put(ins3, "0");
         cv.put(ins4, "0");
         cv.put(ins5, "0");
         cv.put(ins5, "0");
         cv.put(ins7, "0");
         db.insert(tGallery, null, cv);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS "+tHiScore);
		 db.execSQL("DROP TABLE IF EXISTS "+tGallery);
         onCreate(db);
	}
	
	public void setHiScore(String _name, int _score,int _id){
		
		  SQLiteDatabase insectDb = this.getWritableDatabase();
          ContentValues cv = new ContentValues();
          cv.put(fHSId, _id);
          cv.put(fHSName, _name);
          cv.put(fHiScore, _score);
          insectDb.insert(tHiScore, fHSName, cv);
          insectDb.close();	
	}
	
	 public int updateHiScore(String _name, int _score, int _id)
     {
             SQLiteDatabase insectDb=this.getWritableDatabase();
             ContentValues cv=new ContentValues();
             cv.put(fHSId, _id);
             cv.put(fHSName, _name);
             cv.put(fHiScore, _score);
             return insectDb.update(tHiScore, cv, fHSId+"=?", new String []{String.valueOf(_id)});
     }
	 
	 public int getHiScoreCount()
     {
            SQLiteDatabase insectDb=this.getWritableDatabase();
            Cursor cur= insectDb.rawQuery("Select * from "+tHiScore, null);
            int x= cur.getCount();
            cur.close();
            return x;
     }
	 
	 public String getHiScoreName(int _id){
		 if (getHiScoreCount()>=_id){
			 SQLiteDatabase insectDb=this.getReadableDatabase();
	         String[] params=new String[]{String.valueOf(_id)};
	         Cursor c=insectDb.rawQuery("SELECT "+fHSName+" FROM "+ tHiScore+" WHERE "+fHSId+"=?",params);
	         c.moveToFirst();
	         int index= c.getColumnIndex(fHSName);
	         return c.getString(index);
		 }else{
			 return "Player0";
		 }
		 
	 }
	 
	 public int getHiScore(int _id){
		 if (getHiScoreCount()>=_id){
			 SQLiteDatabase insectDb=this.getReadableDatabase();
			 String[] params=new String[]{String.valueOf(_id)};
	         Cursor c=insectDb.rawQuery("SELECT "+fHiScore+" FROM "+ tHiScore+" WHERE "+fHSId+"=?",params);
	         c.moveToFirst();
	         if (c.getColumnIndex(fHiScore)>=0){
		         Log.d("getHiScore","fHiScore "+c.getColumnIndex(fHiScore));
		         return c.getInt(c.getColumnIndex(fHiScore));
	         } else return -1;
		 }else{
			 return 0;
		 }
	 }
	 
	 public void addHiScore(String _name, int _score){
		 int index=((getHiScoreCount()>=HS_INDEX)?HS_INDEX:getHiScoreCount());
		 if (index<=5){
			 setHiScore(_name, _score,index+1);
		 }else{
			 updateHiScore(_name,_score,6);
		 }
		 Log.d("index",""+"index = "+index);
		// if (index>0) Log.d("index","hi score "+(index-1)+" = "+getHiScore(index-1)+" , score index = "+_score);
			 while(index>=1)
			 {		
				 Log.d("index",""+"index = "+index);
				 	if(getHiScore(index)<getHiScore(index+1)){
				 		 Log.d("index",""+"index = "+index+" masuk if.");
				 	 String tempName=getHiScoreName(index);
				 	 int tempScore = getHiScore(index);
					 updateHiScore(getHiScoreName(index+1),getHiScore(index+1),index);
					 updateHiScore(tempName,tempScore,index+1);
				 	}
				 	index--;
			 }
		 
		 Log.d("HiScore","name = "+getHiScoreName(1)+", score = "+getHiScore(1));
	 }
	 
	 public void printHiScore(){
			for (int i =1;i<=5;++i){
				Log.d("HiScore","HiScore "+i+" -> Name = "+getHiScoreName(i)+" ,score = "+getHiScore(i));
			}
		}
	 
	 public void updateGallery(int _longestTime, int _ins1, int _ins2, int _ins3, int _ins4, int _ins5, int _ins6, int _ins7)
     {
             SQLiteDatabase insectDb=this.getWritableDatabase();
             ContentValues cv=new ContentValues();
             if (getLongestTime()<_longestTime){
            	 cv.put(fGLongestTime, _longestTime);
             }
             
             cv.put(fGPlayedAmount, getPlayedAmount()+1);
             
             if (!isCatched(1)&&_ins1>0){
            	 cv.put(ins1, "1");
             }
             
             if (!isCatched(2)&&_ins2>0){
            	 cv.put(ins2, "1");
             }
             
             if (!isCatched(3)&&_ins3>0){
            	 cv.put(ins3, "1");
             }
             
             if (!isCatched(4)&&_ins4>0){
            	 cv.put(ins4, "1");
             }
             
             if (!isCatched(5)&&_ins5>0){
            	 cv.put(ins5, "1");
             }
             
             if (!isCatched(6)&&_ins6>0){
            	 cv.put(ins6, "1");
             }
             
             if (!isCatched(7)&&_ins7>0){
            	 cv.put(ins7, "1");
             }
                        
             Cursor cur= insectDb.rawQuery("Select * from "+tGallery, null);
             int x= cur.getCount();
             if (x>0){
            	 insectDb.update(tGallery, cv, fGId+"=?", new String []{String.valueOf(0)});
             }else{
            	 cv.put(fGId, "0");
            	 insectDb.insert(tGallery, null, cv);
             }
             
             
             Log.d("Gallery","updated");
     }
	 
	 public boolean isCatched(int _id){
		 SQLiteDatabase insectDb=this.getReadableDatabase();
		 String[] params=new String[]{String.valueOf(0)};
		 Cursor c;
		 int result=0;
		 switch(_id){
		 	case 1:
		 		c=insectDb.rawQuery("SELECT "+ins1+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
		        c.moveToFirst();
		        result= c.getInt(c.getColumnIndex(ins1));
		 	case 2:
		 		c=insectDb.rawQuery("SELECT "+ins2+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
		        c.moveToFirst();
		        result= c.getInt(c.getColumnIndex(ins2));
		 	case 3:
		 		c=insectDb.rawQuery("SELECT "+ins3+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
		        c.moveToFirst();
		        result= c.getInt(c.getColumnIndex(ins3));
		 	case 4:
		 		c=insectDb.rawQuery("SELECT "+ins4+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
		        c.moveToFirst();
		        result= c.getInt(c.getColumnIndex(ins4));
		 	case 5:
		 		c=insectDb.rawQuery("SELECT "+ins5+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
		        c.moveToFirst();
		        result= c.getInt(c.getColumnIndex(ins5));
		 	case 6:
		 		c=insectDb.rawQuery("SELECT "+ins6+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
		        c.moveToFirst();
		        result= c.getInt(c.getColumnIndex(ins6));
		 	case 7:
		 		c=insectDb.rawQuery("SELECT "+ins7+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
		        c.moveToFirst();
		        result= c.getInt(c.getColumnIndex(ins7));
		 }
		 return (result==1)?true:false;   
	 }
	 
	 
	 public int getLongestTime(){
		 
		 SQLiteDatabase insectDb=this.getReadableDatabase();
		 
		 Cursor cur= insectDb.rawQuery("Select * from "+tGallery, null);
         int x= cur.getCount();
         if (x>0){
			 String[] params=new String[]{String.valueOf(0)};
	         Cursor c=insectDb.rawQuery("SELECT "+fGLongestTime+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
	         c.moveToFirst();
	         Log.d("getLongestTime","getLongestTime = "+c.getColumnIndex(fGLongestTime));
	         return c.getInt(c.getColumnIndex(fGLongestTime));
         }else{
        	 return 0;
         }
	 }
	 
	 
	 public int getPlayedAmount(){
		 SQLiteDatabase insectDb=this.getReadableDatabase();
		 Cursor cur= insectDb.rawQuery("Select * from "+tGallery, null);
         int x= cur.getCount();
         if (x>0){
			 String[] params=new String[]{String.valueOf(0)};
	         Cursor c=insectDb.rawQuery("SELECT "+fGPlayedAmount+" FROM "+ tGallery+" WHERE "+fGId+"=?",params);
	         c.moveToFirst();
	         return c.getInt(c.getColumnIndex(fGPlayedAmount));
         }else{
        	 return 0;
         }
	 }
	 
	 public void checkGallery(){
		 Log.d("Gallery","Longest Time = "+ getLongestTime());
		 Log.d("Gallery","Total Played = "+ getPlayedAmount());
		 Log.d("Gallery", "Insects status :");
		 if (isCatched(1)){
			 Log.d("Gallery", "Beetle berwarna");
		 }else{
			 Log.d("Gallery", "Beetle abu2");
		 }
		 
		 if (isCatched(2)){
			 Log.d("Gallery", "Ladybug berwarna");
		 }else{
			 Log.d("Gallery", "Ladybug abu2");
		 }
		 
		 if (isCatched(3)){
			 Log.d("Gallery", "Grasshopper berwarna");
		 }else{
			 Log.d("Gallery", "Grasshopper abu2");
		 }
		 
		 if (isCatched(4)){
			 Log.d("Gallery", "Butterfly berwarna");
		 }else{
			 Log.d("Gallery", "Butterfly abu2");
		 }
		 
		 if (isCatched(5)){
			 Log.d("Gallery", "Honey bee berwarna");
		 }else{
			 Log.d("Gallery", "Honey bee abu2");
		 }
		 
		 if (isCatched(6)){
			 Log.d("Gallery", "Golden Dragonfly berwarna");
		 }else{
			 Log.d("Gallery", "Golden Dragonfly abu2");
		 }
		 
		 if (isCatched(7)){
			 Log.d("Gallery", "Time Insect berwarna");
		 }else{
			 Log.d("Gallery", "ime Insect abu2");
		 }
	 }

}
