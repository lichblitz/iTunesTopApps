package org.lichblitz.iapps.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.lichblitz.iapps.R;
import org.lichblitz.iapps.app.MainApplication;

/**
 * Created by lichblitz on 8/05/16.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private String[] mCategories;
    private int[] mCategoriesId;
    private Context context;
    protected static CategoryListener mListener;
    protected static int categorySelected = 0;

    public CategoryAdapter(Context context, String[] categories, int[] categoriesId, CategoryListener listener){
        this.context = context;
        this.mCategories = categories;
        this.mCategoriesId = categoriesId;
        this.mListener = listener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.setCatId(mCategoriesId[position]);
        holder.setCatName(mCategories[position]);
    }

    @Override
    public int getItemCount() {
        return mCategories.length;
    }

    /**
     * Recycler viewholder
     */
    public static final class CategoryHolder extends RecyclerView.ViewHolder{
        Button catButton;
        int catId;
        String catName;

        public CategoryHolder(View itemView) {
            super(itemView);

            catButton = (Button) itemView.findViewById(R.id.btn_category);
            catButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(categorySelected == catId){
                        categorySelected = 0;
                        mListener.categorySelectedCallback(0);
                    }else{
                        categorySelected = catId;
                        mListener.categorySelectedCallback(categorySelected);
                    }
                }
            });

        }

        public void setCatId(int catId) {
            this.catId = catId;
        }

        public void setCatName(String catName) {
            this.catName = catName;
            catButton.setText(catName);
        }
    }

    public interface CategoryListener{
        void categorySelectedCallback(int selected);
    }
}
