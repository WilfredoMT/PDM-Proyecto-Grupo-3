package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;

public class SolicitudesAtendidasAdapter extends RecyclerView.Adapter<SolicitudesAtendidasAdapter.EncargadosViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    private BaseDatosHelper baseDatosHelper;

    public SolicitudesAtendidasAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public EncargadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.solicitudes_propuestas_items, parent, false);
        return new EncargadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  EncargadosViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        String nombre = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nomEvento));
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idEvento));

        Cursor cursor = baseDatosHelper.getEvento(id);
        String idPrioridad = cursor.getString(4);
        cursor  = baseDatosHelper.getPrioridad(idPrioridad, null);
        String ordenS = cursor.getString(1);
        int orden = Integer.parseInt(ordenS);


        // Set click listeners for buttons
        if(orden == 1)
        {



        }
        else {


        }
        if(orden == 2)
        {
            holder.textViewSolicitudes.setText(nombre);


            holder.buttonRechazar.setOnClickListener(v -> {


                //booton de rechazar

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle(R.string.confirmacion);
                String mensaje = mContext.getString(R.string.estas_seguro_de_que_deseas_rechazar_la_propuesta);
                builder.setMessage(mensaje +"\n" + nombre);

                builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {


                        baseDatosHelper.actualizarPrioridad(idPrioridad, "3");
                        Toast.makeText(mContext.getApplicationContext(), R.string.propuesta_rechazada_correctamente, Toast.LENGTH_SHORT).show();
                        SolicitudesPropuestasFragment.getInstance().refreshRecyclerView();

                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Cancelar
                        Toast.makeText(mContext.getApplicationContext(), R.string.accion_cancelada, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            });
        }
        else {
            //holder.textViewSolicitudes.setVisibility(View.GONE);
            holder.buttonRechazar.setVisibility(View.GONE);
            //holder.buttonAceptar.setVisibility(View.GONE);

        }

        if(orden == 3)
        {
            holder.textViewSolicitudes.setText(nombre);
            holder.buttonAceptar.setOnClickListener(v -> {

                Log.e("BotonAceptar", "Acpetado");
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle(R.string.confirmacion);
                String mensaje = mContext.getString(R.string.estas_seguro_de_aceptar_la_propuesta);
                builder.setMessage(mensaje +"\n" + nombre);

                builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {



                        baseDatosHelper.actualizarPrioridad(idPrioridad, "2");
                        Toast.makeText(mContext.getApplicationContext(), R.string.propuesta_aceptada_correctamente, Toast.LENGTH_SHORT).show();
                        SolicitudesPropuestasFragment.getInstance().refreshRecyclerView();

                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Cancelar
                        Toast.makeText(mContext.getApplicationContext(), R.string.accion_cancelada, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create(); // Create the AlertDialog
                alert.show(); // Display the AlertDialog


            });

        }
        else {
            //holder.textViewSolicitudes.setVisibility(View.GONE);
            //holder.buttonRechazar.setVisibility(View.GONE);
            holder.buttonAceptar.setVisibility(View.GONE);

        }







    }



    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null)
            mCursor.close();

        mCursor = newCursor;

        if (newCursor != null)
            notifyDataSetChanged();
    }

    static class EncargadosViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSolicitudes;
        Button buttonAceptar;
        Button buttonRechazar;

        EncargadosViewHolder(View itemView) {
            super(itemView);
            textViewSolicitudes = itemView.findViewById(R.id.textViewSolicitudesPropuestas);
            buttonAceptar = itemView.findViewById(R.id.buttonAceptar);
            buttonRechazar = itemView.findViewById(R.id.buttonRechazar);
        }
    }
}