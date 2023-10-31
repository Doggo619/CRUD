package com.base.crud1;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductEntity> products;
    private Context context;
    private OnItemClickListener listener;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (products != null) {
            ProductEntity product = products.get(position);
            Log.d("ProductAdapter", "Binding product: " + product.getProductName());
            holder.productName.setText(product.getProductName());
            holder.productPrice.setText(String.valueOf(product.getProductPrice()));
            // Set other views accordingly
            holder.productCategory.setText(product.getProductCategory());
            holder.productDescription.setText(product.getProductDescription());
            Log.d("ProductAdapter", "Product Image Uri: " + product.getProductImage());
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            if (!TextUtils.isEmpty(product.getProductImage())) {

                Picasso.get()
                        .load(product.getProductImage())
                        .error(R.drawable.ic_delete)
                        .into(holder.productImage);
            } else {
                Log.d("ProductAdapter", "No image URI provided.");
                // Handle the case when the image path is empty or null (e.g., set a placeholder image)
                holder.productImage.setImageResource(R.drawable.ic_default);
            }
        }
    }


    public ProductEntity getProductAt(int position) {
        if (products != null && position >= 0 && position < products.size()) {
            return products.get(position);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return (products != null) ? products.size() : 0;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        ImageView productImage;
        TextView productCategory;
        TextView productDescription;
        ImageButton editButton;
        ImageButton deleteButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tv_productname);
            productPrice = itemView.findViewById(R.id.tv_productprice);
            productImage = itemView.findViewById(R.id.iv_productimage);
            productCategory = itemView.findViewById(R.id.tv_productcategory);
            productDescription = itemView.findViewById(R.id.tv_productdescription);
            editButton = itemView.findViewById(R.id.btn_edit);
            deleteButton = itemView.findViewById(R.id.btn_delete);
        }
    }
}

