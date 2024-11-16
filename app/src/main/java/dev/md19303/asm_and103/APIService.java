package dev.md19303.asm_and103;

import java.util.List;

import dev.md19303.asm_and103.Model.CakeModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    String DOMAIN = "http://192.168.1.12:3000"; // Địa chỉ server Node.js

    // Lấy danh sách bánh
    @GET("/")
    Call<List<CakeModel>> getCake();

    // Thêm một bánh mới
    @POST("/add_cake")
    @Headers("Content-Type: application/json")
    Call<Void> addCake(@Body CakeModel cake);

    // Xóa một bánh
    @GET("/delete/{id}")
    Call<Void> deleteCake(@Path("id") String id);

    @PUT("/update/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> updateCake(@Path("id") String id, @Body CakeModel updatedCake);
}
