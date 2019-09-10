package com.kraos.swipeexpandlistview.Demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kraos.swipeexpandlistview.ListView.ExpandableListViewDividerAdapter;
import com.kraos.swipeexpandlistview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Description: Demo的Fragment
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/9/10 10:37
 */
public class ExpandableDemoFragment extends Fragment {
    private View root;
    private ExpandableListView expandableListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(root==null){
            root = inflater.inflate(R.layout.fragment_expandablelistview,container,false);
            expandableListView = (ExpandableListView) root.findViewById(R.id.expandable_listview);
            expandableListView.setAdapter(new ExpandableListViewDividerAdapter(new ChildListViewAdapter()));
        }
        return root;
    }

    class ChildListViewAdapter extends BaseExpandableListAdapter{
        int[] resIds = new int[]{R.layout.item_child,R.layout.item_child,R.layout.item_child,R.layout.item_child};
        List<List<Integer>> data;
        Handler handler;

        ChildListViewAdapter(){
            data = new ArrayList<>();
            for(int i=0;i<10;i++){
                List<Integer> ids = new ArrayList<>();
                for(int j=0;j<resIds.length;j++)
                    ids.add(resIds[j]);
                data.add(ids);
            }

            handler = new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    notifyDataSetChanged();
                    super.handleMessage(msg);
                }
            };
        }

        void delete(int groupPosition,int childPosition){
            data.get(groupPosition).remove(childPosition);
            notifyDataSetInvalidated();
        }

        @Override
        public int getGroupCount() {
            return data.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return data.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return "FileFolder "+groupPosition;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return data.get(groupPosition).get(childPosition);
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView==null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expandablelistview_group,parent,false);
            ((TextView)convertView).setText(String.format(Locale.getDefault(),"FileFolder %d",groupPosition));
            return convertView;
        }

        /**
         *  通过该方法把自定义的折叠子布局加载到view 以及设置对应的点击回调
         * @param groupPosition
         * @param childPosition
         * @param isLastChild
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if(convertView==null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expandablelistview_child,parent,false);
            View childView = (View) getLayoutInflater().inflate(data.get(groupPosition).get(childPosition),null);
            RelativeLayout childContentRL = convertView.findViewById(R.id.item_content_child);
            childContentRL.addView(childView);
            convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(groupPosition,childPosition);
                }
            });
            convertView.findViewById(R.id.btn_item_swipe_mail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"查看邮件",Toast.LENGTH_SHORT).show();

                }
            });
            convertView.findViewById(R.id.item_content_child).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"点击了"+data.get(groupPosition).get(childPosition),Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return getCombinedGroupId(groupPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return getCombinedChildId(groupPosition,childPosition);
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
