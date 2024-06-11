<?php

namespace App\Http\Controllers;

use App\Models\Equipe;
use App\Models\Finition;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

class EquipeController extends Controller
{
    public function list() {
        $obj = new Equipe();
        $equipe = $obj->getAllEquipe();
        return view('equipe.list_equipe',[
            'equipes' => $equipe
        ]);
    }

//    public function equipeInsertView() {
//        return view('equipe.insert_equipe');
//    }

    public function insert(Request $request){
        $request->validate(
        [
		'nom'=> ['required'],
		'pwd'=> ['required'],
		'login'=> ['required'],
	]
        );
        try {
            $id = DB::table('equipe')->insertGetId([
		'nom'=> $request->nom,
		'pwd'=> $request->pwd,
		'login'=> $request->login,
	],'id');
            return back()->with('message',['Inserer equipe avec succes']);
        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

    public function equipeEditView($id) {
        $equipe = Equipe::getEquipeById($id);

        return view('equipe.update_equipe',[
            'equipe' => $equipe
        ]);
    }

    public function equipeEdit(Request $request) {
        $request->validate(
        [
		'nom'=> ['required'],
		'pwd'=> ['required'],
		'login'=> ['required'],
	]
        );

        try {
            DB::table('equipe')
                ->where('id', $request->id)
                ->update(
                    [
		'nom'=> $request->nom,
		'pwd'=> $request->pwd,
		'login'=> $request->login,
	]
                );

            return redirect('/equipe')->with('message',['Modier avec succes']);

        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

    public function delete($id)
    {
        try {
            DB::table('equipe')
                ->where('id',$id)
                ->delete();
            return back()->with('success',['Supprimer avec succes']);
        } catch (\Exception $e) {
            return back()->with('errors',$e->getMessage());
        }
    }

}
