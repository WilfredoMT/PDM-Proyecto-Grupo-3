package sv.edu.ues.fia.pdm.proyecto.grupo3.ui.solicitudes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sv.edu.ues.fia.pdm.proyecto.grupo3.BaseDatosHelper;
import sv.edu.ues.fia.pdm.proyecto.grupo3.R;
import sv.edu.ues.fia.pdm.proyecto.grupo3.ui.ciclo.CicloAdapter;

public class SolicitudesPropuestasAdapter extends RecyclerView.Adapter<SolicitudesPropuestasAdapter.SolicitudesPropuestasViewHolder>{

    private Cursor mCursor;
    private Context mContext;

    private BaseDatosHelper baseDatosHelper;

    public SolicitudesPropuestasAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public SolicitudesPropuestasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.solicitudespropuestas_items, parent, false);
        return new SolicitudesPropuestasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudesPropuestasAdapter.SolicitudesPropuestasViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SolicitudesPropuestasViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSolicitud;
        Button buttonVer;
        Button buttonDelete;

        SolicitudesPropuestasViewHolder(View itemView) {
            super(itemView);
            textViewSolicitud = itemView.findViewById(R.id.textViewSolProp);
            buttonVer = itemView.findViewById(R.id.buttonVer);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

    }
}
