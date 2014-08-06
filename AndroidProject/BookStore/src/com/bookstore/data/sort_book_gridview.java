package com.bookstore.data;

import java.util.List;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.bookstore.activity.R;

public class sort_book_gridview extends BaseAdapter {
	private LayoutInflater inflater; 
    private List<sort_book_bean> pictures; 
    private RequestQueue mQueue;

    public sort_book_gridview(List<sort_book_bean> pictures, Context context,RequestQueue one ){
    	super();
        this.pictures=pictures;
        this.mQueue=one;
        inflater = LayoutInflater.from(context);
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		 if (null != pictures) 
	        { 
	            return pictures.size(); 
	        } else
	        { 
	            return 0; 
	        } 
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		  return pictures.get(arg0); 
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		  return arg0; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder; 
		// TODO Auto-generated method stub
		 if (convertView == null) 
	        { 
	            convertView = inflater.inflate(R.layout.gridview_sort_book, null); 
	            viewHolder = new ViewHolder(); 
	          
	            viewHolder.text = (Text) convertView.findViewById(R.id.sort_book_text_name);
	           
	            convertView.setTag(viewHolder); 
	            
	        } else
	        { 
	            viewHolder = (ViewHolder) convertView.getTag(); 
	        } 
	        
		 return convertView; 
	}
	class ViewHolder 
	{ 

	    public Text text; 

	}

}
