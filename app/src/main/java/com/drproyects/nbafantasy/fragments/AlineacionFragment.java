package com.drproyects.nbafantasy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.drproyects.nbafantasy.ObjectJugador;
import com.drproyects.nbafantasy.ObjectUsuario;
import com.drproyects.nbafantasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlineacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlineacionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Log:";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ViewPager viewPager;
    ImageButton btnBase, btnEscolta, btnAlero, btnAlaPivot, btnPivot, btnSexto, btnSeptimo, btnOctavo, btnNoveno, btnDecimo;
    TextView base, escolta, alero, alaPivot, pivot, sexto, septimo, octavo, noveno, decimo;
    Button btnVenderJugador;

    Boolean venderJugador;
    List<JSONObject> jugadoresAlineados = new ArrayList<>();

    RequestQueue requestQueue;

    public AlineacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlineacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlineacionFragment newInstance(String param1, String param2) {
        AlineacionFragment fragment = new AlineacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        requestQueue = Volley.newRequestQueue(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_alineacion, container, false);

        venderJugador = false;

        base = root.findViewById(R.id.nombreBase);
        escolta = root.findViewById(R.id.nombreEscolta);
        alero = root.findViewById(R.id.nombreAlero);
        alaPivot = root.findViewById(R.id.nombreAlaPivot);
        pivot = root.findViewById(R.id.nombrePivot);
        sexto = root.findViewById(R.id.nombreSexto);
        septimo = root.findViewById(R.id.nombreSeptimo);
        octavo = root.findViewById(R.id.nombreOctavo);
        noveno = root.findViewById(R.id.nombreNoveno);
        decimo = root.findViewById(R.id.nombreDecimo);

        btnBase = root.findViewById(R.id.base);
        btnEscolta = root.findViewById(R.id.escolta);
        btnAlero = root.findViewById(R.id.alero);
        btnAlaPivot = root.findViewById(R.id.ala_pivot);
        btnPivot = root.findViewById(R.id.pivot);
        btnSexto = root.findViewById(R.id.sexto);
        btnSeptimo = root.findViewById(R.id.septimo);
        btnOctavo = root.findViewById(R.id.octavo);
        btnNoveno = root.findViewById(R.id.noveno);
        btnDecimo = root.findViewById(R.id.decimo);
        
        btnVenderJugador = root.findViewById(R.id.btnVenderJugador);
        viewPager = root.findViewById(R.id.viewPager);
        ObjectUsuario usuario = (ObjectUsuario) getActivity().getIntent().getSerializableExtra("usuario");

        btnBase.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, base, "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnEscolta.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, escolta, "2");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnAlero.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, alero, "3");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnAlaPivot.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, alaPivot, "4");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnPivot.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, pivot, "5");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnSexto.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, sexto, "6");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnSeptimo.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, septimo, "7");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnOctavo.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, octavo, "8");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnNoveno.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, noveno, "9");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        btnDecimo.setOnClickListener(v -> {
            try {
                jugadorSeleccionado(v, jugadoresAlineados, decimo, "10");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        btnVenderJugador.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            venderJugador = true;
            bundle.putBoolean("vender", venderJugador);
            Fragment fragment = new MiEquipoFragment();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment, null);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
            btnVenderJugador.setVisibility(v.GONE);
        });

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.0.3/bddfantasy/alineaciones/getAlineacionById.php?idUsuario=" + usuario.getIdUsuario(),
                null,
                response -> {
                    try {
                        jugadoresAlineados.clear();
                        for(int x = 0; x < response.length(); x++){
                            jugadoresAlineados.add(response.getJSONObject(x));
                        }
                        colocarJugadores(jugadoresAlineados);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    //Toast.makeText(getContext(), "Error al obtener la alineación", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);


        return root;
    }

    private void jugadorSeleccionado(View v, List<JSONObject> jugadoresAlineados, TextView nombreJugador, String pos) throws JSONException {
        Bundle bundle = new Bundle();
        for(int i = 0 ; i<jugadoresAlineados.size() ; i++){
            if(nombreJugador.getText().toString().equals(jugadoresAlineados.get(i).getString("nombreJugador"))){
                    bundle.putString("id", jugadoresAlineados.get(i).getString("idJugador"));
            }
        }
        bundle.putString("pos", pos);

        Fragment fragment = new MiEquipoFragment();
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment, null);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
        btnVenderJugador.setVisibility(v.GONE);
    }

    private void colocarJugadores(List<JSONObject> jugadoresAlineados) throws JSONException {
        for(int i=0 ; i<jugadoresAlineados.size() ; i++){
            String nombre = jugadoresAlineados.get(i).getString("nombreJugador");
            String posicion = jugadoresAlineados.get(i).getString("posicionAlineacionJugador");
            switch (posicion){
                case "1":
                    base.setText(nombre);
                    btnBase.setImageResource(R.drawable.nba_logo);
                    break;
                case "2":
                    escolta.setText(nombre);
                    btnEscolta.setImageResource(R.drawable.nba_logo);
                    break;
                case "3":
                    alero.setText(nombre);
                    btnAlero.setImageResource(R.drawable.nba_logo);
                    break;
                case "4":
                    alaPivot.setText(nombre);
                    btnAlaPivot.setImageResource(R.drawable.nba_logo);
                    break;
                case "5":
                    pivot.setText(nombre);
                    btnPivot.setImageResource(R.drawable.nba_logo);
                    break;
                case"6":
                    sexto.setText(nombre);
                    btnSexto.setImageResource(R.drawable.nba_logo);
                    break;
                case"7":
                    septimo.setText(nombre);
                    btnSeptimo.setImageResource(R.drawable.nba_logo);
                    break;
                case"8":
                    octavo.setText(nombre);
                    btnOctavo.setImageResource(R.drawable.nba_logo);
                    break;
                case"9":
                    noveno.setText(nombre);
                    btnNoveno.setImageResource(R.drawable.nba_logo);
                    break;
                case "10":
                    decimo.setText(nombre);
                    btnDecimo.setImageResource((R.drawable.nba_logo));
            }
        }
    }


}