<?php

namespace App\Http\Controllers;

use App\Models\Temps_coureur;
use App\Models\Finition;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class Temps_coureurController extends Controller
{
    public function list() {
        $obj = new Temps_coureur();
        $temps_coureur = $obj->getAllTemps_coureur();
        return view('temps_coureur.list_temps_coureur',[
            'temps_coureurs' => $temps_coureur
        ]);
    }

//    public function temps_coureurInsertView() {
//        return view('temps_coureur.insert_temps_coureur');
//    }

    public function insert(Request $request){
        $request->validate(
        [
		'heure_depart'=> ['required'],
		'heure_arrive'=> ['required'],
		'penalite'=> ['required'],
		'id_etape'=> ['required'],
		'id_coureur'=> ['required'],
	]
        );
        try {
            $id = DB::table('temps_coureur')->insertGetId([
		'heure_depart'=> $request->heure_depart,
		'heure_arrive'=> $request->heure_arrive,
		'penalite'=> $request->penalite,
		'id_etape'=> $request->id_etape,
		'id_coureur'=> $request->id_coureur,
	],'id');
            return back()->with('message',['Inserer temps_coureur avec succes']);
        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

    public function temps_coureurEditView($id) {
        $temps_coureur = Temps_coureur::getTemps_coureurById($id);

        return view('temps_coureur.update_temps_coureur',[
            'temps_coureur' => $temps_coureur
        ]);
    }

    public function temps_coureurEdit(Request $request) {
        $request->validate(
        [
		'heure_depart'=> ['required'],
		'heure_arrive'=> ['required'],
		'penalite'=> ['required'],
		'id_etape'=> ['required'],
		'id_coureur'=> ['required'],
	]
        );

        try {
            DB::table('temps_coureur')
                ->where('id', $request->id)
                ->update(
                    [
		'heure_depart'=> $request->heure_depart,
		'heure_arrive'=> $request->heure_arrive,
		'penalite'=> $request->penalite,
		'id_etape'=> $request->id_etape,
		'id_coureur'=> $request->id_coureur,
	]
                );

            return redirect('/temps_coureur')->with('message',['Modier avec succes']);

        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

    public function delete($id)
    {
        try {
            DB::table('temps_coureur')
                ->where('id',$id)
                ->delete();
            return back()->with('success',['Supprimer avec succes']);
        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

}
