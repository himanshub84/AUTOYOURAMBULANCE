package e.himanshu.loginmodule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter {
    List<HELP> helplist;

    public HelperAdapter(List<HELP> helplist) {
        this.helplist = helplist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_recycler_alldata,parent,false);
        ViewHolderClass viewHolderClass=new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass=(ViewHolderClass)holder;
        HELP help=helplist.get(position);
        viewHolderClass.name.setText(help.getName());
        viewHolderClass.phone.setText(help.getPHN());
        viewHolderClass.loc.setText(help.getLOC());
        viewHolderClass.mssge.setText(help.getPSTMSSG());
        viewHolderClass.loclink.setText(help.getLOCLINK());

    }

    @Override
    public int getItemCount() {
        return helplist.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{
        TextView name,phone,loc,mssge,loclink;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text_row_name);
            phone=itemView.findViewById(R.id.text_row_phone);
            loc=itemView.findViewById(R.id.text_row_location);
            mssge=itemView.findViewById(R.id.text_row_postmssge);
            loclink=itemView.findViewById(R.id.text_row_location_link);
        }
    }


}
