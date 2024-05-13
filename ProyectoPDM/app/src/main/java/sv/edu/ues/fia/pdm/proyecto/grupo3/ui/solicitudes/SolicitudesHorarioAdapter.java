package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloAdapter;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloFragment;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.EditarCicloActivity;

import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
public class SolicitudesHorarioAdapter extends RecyclerView.Adapter<SolicitudesHorarioAdapter.SolicitudesHorarioViewHolder>{
    private Cursor mCursor;
    private Context mContext;
    public String nombreUsuario;
    private BaseDatosHelper baseDatosHelper;

    public SolicitudesHorarioAdapter(Context context, Cursor cursor, String nomUsuario) {
        mContext = context;
        mCursor = cursor;
        nombreUsuario = nomUsuario;

    }

    @NonNull
    @Override
    public SolicitudesHorarioAdapter.SolicitudesHorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.solicitudes_horario_items, parent, false);
        return new SolicitudesHorarioAdapter.SolicitudesHorarioViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SolicitudesHorarioAdapter.SolicitudesHorarioViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;


        baseDatosHelper = new BaseDatosHelper(mContext.getApplicationContext());

        Cursor cursor = baseDatosHelper.getUsuario(nombreUsuario);
        String idUsuarioLogueado = cursor.getString(0);


        cursor = baseDatosHelper.getCoordinador(idUsuarioLogueado, null, null);
        String idCoordinadorLogueado = cursor.getString(0);
        String nombreCoordinadorLogueado = cursor.getString(1);


        String nomEvento = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_nomEvento));
        String id = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idEvento));

        Cursor cursor3 = baseDatosHelper.getEvento(id);
        String idPrioridad = cursor3.getString(4);
        String idHorario = cursor3.getString(3);
        String idPropuesta = mCursor.getString(mCursor.getColumnIndexOrThrow(BaseDatosHelper.KEY_idPropuestaEvento));
        Cursor cursor2 = baseDatosHelper.getPropuesta(idPropuesta);
        String idCoordinador = cursor2.getString(cursor2.getColumnIndexOrThrow(BaseDatosHelper.KEY_idCoordinadorPropuesta));
        Log.e("idCoordinadorEncontrado", idCoordinador);
        Log.e("idCoordinadorLogueado", idCoordinadorLogueado);

        int idCoord = Integer.parseInt(idCoordinador);
        int idCoordLog = Integer.parseInt(idCoordinadorLogueado);

        if (idCoord == idCoordLog)
        {

            holder.textViewSolicitudesHorario.setText(nomEvento);

            // Set click listeners for buttons
            holder.buttonEdit.setOnClickListener(v -> {

           Intent intent = new Intent(mContext, EditarSolicitudesHorarioActivity.class);
            intent.putExtra("idEditar", id);
                intent.putExtra("idCoordinador", idCoordinador);
            mContext.startActivity(intent);


                // Handle edit button click
            });

            holder.buttonDelete.setOnClickListener(v -> {



            //booton de borrar

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            builder.setTitle(R.string.confirmacion);
            String mensaje = mContext.getString(R.string.estas_seguro_que_deseas_borrar_la_solicitud);
            builder.setMessage(mensaje +"\n" + nomEvento);

            builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {


                    baseDatosHelper.eliminarHorario(idHorario);


                    baseDatosHelper.eliminarPropuesta(idPropuesta);


                    baseDatosHelper.eliminarPrioridad(idPrioridad);

                    baseDatosHelper.eliminarEvento(id);

                    Toast.makeText(mContext.getApplicationContext(), R.string.solicitud_borrada_correctamente, Toast.LENGTH_SHORT).show();
                    SolicitudesHorarioFragment.getInstance().refreshRecyclerView();
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
        }else {
                holder.buttonEdit.setVisibility(View.GONE);
                holder.buttonDelete.setVisibility(View.GONE);
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

    static class SolicitudesHorarioViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSolicitudesHorario;
        Button buttonEdit;
        Button buttonDelete;

        SolicitudesHorarioViewHolder(View itemView) {
            super(itemView);
            textViewSolicitudesHorario = itemView.findViewById(R.id.textViewSolicitudesHorario);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
