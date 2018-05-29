package edu.dartmouth.cs.d_path.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.dartmouth.cs.d_path.Activies.CourseDescriptionActivity;
import edu.dartmouth.cs.d_path.Model.Course;
import edu.dartmouth.cs.d_path.R;

/**
 * Created by jameslee on 5/28/18.
 */
public class AllCourseAdapter extends RecyclerView.Adapter<AllCourseAdapter.ViewHolder> {
    private static final String TAG="AllCourseAdapter";
    private Context context;
    private LayoutInflater inflater;
    ArrayList<Course> courses = new ArrayList<>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title;
        TextView number;
        ImageView save;
        LinearLayout layout;
        public ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.child_course_title);
            number = itemView.findViewById(R.id.child_course_number);
            layout = itemView.findViewById(R.id.all_course_row);
            save = itemView.findViewById(R.id.child_save_icon);


            layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "Item Clicked at "+ getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, CourseDescriptionActivity.class);
                    intent.putExtra("course_number",courses.get(getAdapterPosition()).courseNumber);
                    intent.putExtra("course_description", courses.get(getAdapterPosition()).description);
                    intent.putExtra("course_title", courses.get(getAdapterPosition()).title);
                    intent.putExtra("course_instructors", courses.get(getAdapterPosition()).instructors);
                    intent.putExtra("course_distributives", courses.get(getAdapterPosition()).distributives);
                    intent.putExtra("course_offered", courses.get(getAdapterPosition()).offered);
                    context.startActivity(intent);

                }
            });

            save.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Toast.makeText(view.getContext(), "SAVE at "+ getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child("user_" + FirebaseAuth.getInstance().getUid()).child("saved").child(courses.get(getAdapterPosition()).courseNumber).setValue(courses.get(getAdapterPosition()));
                }
            });



        }



    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public AllCourseAdapter(Context context, ArrayList<Course> courses) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.courses = courses;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AllCourseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View view = inflater.inflate(R.layout.all_course_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Course currentCourse = courses.get(position);
        holder.title.setText(currentCourse.title);
        holder.number.setText(currentCourse.courseNumber);
        changeColor(holder, currentCourse);

    }

    public void changeColor(ViewHolder holder, Course currentCourse){
        String courseNumb = currentCourse.getCourseNumber();
        String courseTitle = courseNumb.substring(0, Math.min(courseNumb.length(), 4));
        final int sdk = android.os.Build.VERSION.SDK_INT;

        if (courseTitle.equals("ECON")){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.number.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_shape2) );
            } else {
                holder.number.setBackground(ContextCompat.getDrawable(context, R.drawable.recycler_shape2));
            }
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.darkGreen));
        } else if (courseTitle.equals("BIOL")){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.number.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_orange) );
            } else {
                holder.number.setBackground(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_orange));
            }
            holder.number.setTextColor(ContextCompat.getColor(context, R.color.darkOrange));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.darkOrange));
        } else if(courseTitle.equals("COSC")){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.number.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_blue) );
            } else {
                holder.number.setBackground(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_blue));
            }
            holder.number.setTextColor(ContextCompat.getColor(context, R.color.darkBlue));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.darkBlue));
        } else if (courseTitle.equals("HIST")) {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.number.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_red) );
            } else {
                holder.number.setBackground(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_red));
            }
            holder.number.setTextColor(ContextCompat.getColor(context, R.color.darkRed));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.darkRed));
        } else if(courseTitle.equals("GOVT")){
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.number.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_turq) );
            } else {
                holder.number.setBackground(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_turq));
            }
            holder.number.setTextColor(ContextCompat.getColor(context, R.color.darkTurq));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.darkTurq));
        } else if(courseTitle.equals("ENGS")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.number.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_purple));
            } else {
                holder.number.setBackground(ContextCompat.getDrawable(context, R.drawable.recycler_shape2_purple));
            }
            holder.number.setTextColor(ContextCompat.getColor(context, R.color.darkPurple));
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.darkPurple));

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void delete(int position){
        courses.remove(position);
        notifyItemRemoved(position);
    }
}