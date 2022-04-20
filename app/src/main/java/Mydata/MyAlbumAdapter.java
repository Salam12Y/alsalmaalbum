package Mydata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import abbas.samih.salamalbums.R;

public class MyAlbumAdapter extends ArrayAdapter<MyAlbum> {
    public MyAlbumAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    /**
     * ملائمة المعطى طريقة عرضه
     * تقوم باخذ المعطى من قاعدة البايانات وبناء واجهة وعرض هذه البيانات على الواجهة
     * وتقوم بارجاع الواجهة لكل معطى
     *
     * @param position    رقم المعطى
     * @param convertView
     * @param parent
     * @return تعيد واجهة عرض لمعطى واحد حسب رقمه -
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //View v=View.inflate(getContext(), R.layout.task_item_layout,parent);
        // بناء واجهة لمعطى واحد
        View v = LayoutInflater.from(getContext()).inflate(R.layout.item_album, parent, false);
        //استخراج المعطى حسب رقمه
        MyAlbum item = getItem(position);
        //تجهيز مؤشر لكل كائن على الواجهة
        TextView name=v.findViewById(R.id.tvItemName);
        TextView content=v.findViewById(R.id.tvItemContent);
        //وضع قيم المعطى المستخرج على كائنات الواجهة
        name.setText(item.getName());
        content.setText(item.getContent());

        return v;
    }
}

