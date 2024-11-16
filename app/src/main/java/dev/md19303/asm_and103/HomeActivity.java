package dev.md19303.asm_and103;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dev.md19303.asm_and103.Adapter.CakeAdapter;
import dev.md19303.asm_and103.Model.CakeModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements CakeAdapter.OnCakeInteractionListener {

    private RecyclerView rvCakes;
    private FloatingActionButton fabAdd;
    private List<CakeModel> cakesList;
    private CakeAdapter cakeAdapter;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvCakes = findViewById(R.id.rv_cakes);
        fabAdd = findViewById(R.id.fab_add);
        rvCakes.setLayoutManager(new LinearLayoutManager(this));

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);

        fetchCakes();

        fabAdd.setOnClickListener(view -> showAddDialog());
    }

    // Fetch cakes
    private void fetchCakes() {
        Call<List<CakeModel>> call = apiService.getCake();
        call.enqueue(new Callback<List<CakeModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CakeModel>> call, @NonNull Response<List<CakeModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cakesList = response.body();
                    cakeAdapter = new CakeAdapter(HomeActivity.this, cakesList, HomeActivity.this);
                    rvCakes.setAdapter(cakeAdapter);
                } else {
                    Log.e("API Response", "Failed to fetch cakes");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CakeModel>> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
            }
        });
    }

    // Show dialog to add a new cake
    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_cake, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etDescription = dialogView.findViewById(R.id.et_description);
        EditText etPrice = dialogView.findViewById(R.id.et_price);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            String price = etPrice.getText().toString();

            if (!name.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
                CakeModel newCake = new CakeModel(description, name, Integer.parseInt(price));
                addCake(newCake);
            } else {
                Toast.makeText(HomeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    // Add a new cake
    private void addCake(CakeModel cake) {
        Call<Void> call = apiService.addCake(cake);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Cake added successfully", Toast.LENGTH_SHORT).show();
                    fetchCakes();
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to add cake", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDeleteCake(int position) {
        CakeModel cake = cakesList.get(position);
        Call<Void> call = apiService.deleteCake(cake.get_id());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Cake deleted successfully", Toast.LENGTH_SHORT).show();
                    cakesList.remove(position);
                    cakeAdapter.notifyItemRemoved(position);
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to delete cake", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onEditCake(int position) {
        CakeModel cake = cakesList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_cake, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.et_name);
        EditText etDescription = dialogView.findViewById(R.id.et_description);
        EditText etPrice = dialogView.findViewById(R.id.et_price);

        etName.setText(cake.getName());
        etDescription.setText(cake.getDescription());
        etPrice.setText(String.valueOf(cake.getPrice()));

        builder.setPositiveButton("Update", (dialog, which) -> {
            String name = etName.getText().toString();
            String description = etDescription.getText().toString();
            String price = etPrice.getText().toString();

            if (!name.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
                CakeModel updatedCake = new CakeModel(description, name, Integer.parseInt(price));
                updateCake(cake.get_id(), updatedCake);
            } else {
                Toast.makeText(HomeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void updateCake(String id, CakeModel updatedCake) {
        Call<Void> call = apiService.updateCake(id, updatedCake);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Cake updated successfully", Toast.LENGTH_SHORT).show();
                    fetchCakes();
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to update cake", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
            }
        });
    }
}
