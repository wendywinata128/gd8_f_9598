package com.wendywinata.gd8_f_9598;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsUserFragment extends DialogFragment {

    private TextView tvNama,tvNim, tvFakultas,tvProdi,tvJenisKelamin;

    private String sIdUser, sNama, sFakultas , sProdi, sJenisKelamin,sNim;

    private ImageButton ibClose;
    private ProgressDialog progressDialog;

    public static DetailsUserFragment newInstance(){
        return new DetailsUserFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_details_user, container, false);

        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.show();

        ibClose = v.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvNama = v.findViewById(R.id.tvNama);
        tvNim = v.findViewById(R.id.tvNim);
        tvFakultas = v.findViewById(R.id.tvFakultas);
        tvProdi = v.findViewById(R.id.tvProdi);
        tvJenisKelamin = v.findViewById(R.id.tvJenisKelamin);

        sIdUser = getArguments().getString("id","");
        loadUserById(sIdUser);
        return v;
    }

    private void loadUserById(String id){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> add = apiService.getUserById(id,"data");

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                sNama = response.body().getUsers().get(0).getNama();
                sNim = response.body().getUsers().get(0).getNim();
                sFakultas = response.body().getUsers().get(0).getFakultas();
                sProdi = response.body().getUsers().get(0).getProdi();
                sJenisKelamin = response.body().getUsers().get(0).getJenis_kelamin();

                tvNama.setText(sNama);
                tvNim.setText(sNim);
                tvFakultas.setText(sFakultas);
                tvProdi.setText(sProdi);
                tvJenisKelamin.setText(sJenisKelamin);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Kesalahan Jaringan",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}