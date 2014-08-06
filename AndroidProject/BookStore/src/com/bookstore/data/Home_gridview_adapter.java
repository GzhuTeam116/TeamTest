package com.bookstore.data;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.bookstore.activity.R;




import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class Home_gridview_adapter extends BaseAdapter{ // gridview �첽���� ͼƬ ʹ�÷�ʽ  ������ list ������context ������ �������

	private LayoutInflater inflater; 
    private List<Home_gridview_bean> pictures; 
    private RequestQueue mQueue;
 
    public  Home_gridview_adapter (List<Home_gridview_bean> pictures, Context context,RequestQueue one) 
    { 
        super(); 
        this.pictures=pictures;
        this.mQueue=one;
        inflater = LayoutInflater.from(context);
    
    } 
 
    @Override
    public int getCount() 
    { 
        if (null != pictures) 
        { 
            return pictures.size(); 
        } else
        { 
            return 0; 
        } 
    } 
 
    @Override
    public Object getItem(int position) 
    { 
        return pictures.get(position); 
    } 
 
    @Override
    public long getItemId(int position) 
    { 
        return position; 
    } 
 
    public View getView(int position, View convertView, ViewGroup parent ) 
    { 
        ViewHolder viewHolder; 
        if (convertView == null) 
        { 
            convertView = inflater.inflate(R.layout.hobby_gridviewitem, null); 
            viewHolder = new ViewHolder(); 
          
            viewHolder.image = (ImageView) convertView.findViewById(R.id.iv_hobbyicon);
           
            convertView.setTag(viewHolder); 
            
            final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);  
	        ImageCache imageCache = new ImageCache() {  
	            @Override  
	            public void putBitmap(String key, Bitmap value) {  
	                lruCache.put(key, value);  
	            }  
	  
	            @Override  
	            public Bitmap getBitmap(String key) {  
	                return lruCache.get(key);  
	            }  
	        };  
	        
	        ImageLoader imageLoader = new ImageLoader(mQueue, imageCache);  
	        ImageListener listener = ImageLoader.getImageListener(viewHolder.image, R.drawable.ic_launcher,R.drawable.ic_launcher);  
	        imageLoader.get(pictures.get(position).getBook_img(), listener);
	        
	      
            
        } else
        { 
            viewHolder = (ViewHolder) convertView.getTag(); 
        } 
        
       
      
       // viewHolder.image.setImageResource(pictures.get(position).getUrl()); 
        return convertView; 
    }
    

    
 
} 

class ViewHolder 
{ 

    public ImageView image; 

}
	
	

