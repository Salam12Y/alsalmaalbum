package Mydata;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import abbas.samih.salamalbums.R;

public class MyPicAdapter extends ArrayAdapter<MyPic> {
    public MyPicAdapter(@NonNull Context context, int resource) {
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
        MyPic item = getItem(position);
        //تجهيز مؤشر لكل كائن على الواجهة
        TextView name=v.findViewById(R.id.tvItemName);
        name.setVisibility(View.GONE);
        TextView content=v.findViewById(R.id.tvItemContent);
        content.setVisibility(View.GONE);

        //وضع قيم المعطى المستخرج على كائنات الواجهة
        name.setText(item.getName());
        View vitem= LayoutInflater.from(getContext()).inflate(R.layout.item_album,parent,false);
        ImageView imageView =vitem.findViewById(R.id.imageView);
        downloadImageToLocalFile(item.getImage(),imageView);   //connect item view to data source
        //building item view


        return v;
    }
    private void downloadImageToLocalFile(String fileURL, final ImageView toView) {
        StorageReference httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileURL);
        final File localFile;
        try {
            localFile = File.createTempFile("images", "jpg");


            httpsReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Toast.makeText(getContext(), "downloaded Image To Local File", Toast.LENGTH_SHORT).show();
                    toView.setImageURI(Uri.fromFile(localFile));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Toast.makeText(getContext(), "onFailure downloaded Image To Local File "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    exception.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
