package co.amdit.wingstask;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Holder> {
    List<DataModel> dataModelList;
    Context context;
    /*
    create a constructor for RecycleAdapter class
     */
    public RecycleAdapter(List<DataModel> dataModelList,Context context) {
        this.dataModelList = dataModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout of recyclerView item.
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,null);

        return new Holder(view);
    }
    // bind the holder to the view Item
    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
            //create an object of DataModel and assign them the dataModelList of that position;
            final DataModel dm = dataModelList.get(position);
            //set the textViews by geting them through DataModel object
            holder.eName.setText("Name : "+dm.getEmpName());
            holder.eEmail.setText("Email Id : "+dm.getEmpEmail());
            holder.ePhone.setText("Phone No. : "+dm.getEmpPhone());
            holder.eCompany.setText("Company Name : "+dm.getEmpCompany());
            //define the delete button onClick event.
            holder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create an object from DatabaseHelper class
                    DatabaseHelper dh = new DatabaseHelper(context);

                    dh.deleteData(dm.getId());  //delete the record from database of that particular ID
                    removeItem(position); //remove that View from the recyclerView


                }
            });
            //define update button click event.
            holder.bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go to the InsertData activity to perform update
                   Intent i = new Intent(context,InsertData.class);

                   i.putExtra("ID",dm.getId()); //send the id which we want to update.
                   i.putExtra("visible",View.VISIBLE);
                   i.putExtra("invisible",View.INVISIBLE);

                   context.startActivity(i);

                }
            });
    }
    public void removeItem(int position){
        /*
        remove item from recylerView from the position
         */
        dataModelList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView eName,eEmail,ePhone,eCompany;
        ImageButton bt_delete,bt_update;

        public Holder(View itemView) {
            super(itemView);
            eName =(TextView) itemView.findViewById(R.id.eName);
            eEmail= (TextView)itemView.findViewById(R.id.eEmail);
            ePhone = (TextView)itemView.findViewById(R.id.ePhone);
            eCompany=(TextView)itemView.findViewById(R.id.eCompany);
            bt_delete = (ImageButton)itemView.findViewById(R.id.delete);
            bt_update = (ImageButton)itemView.findViewById(R.id.update);

        }
    }
}
