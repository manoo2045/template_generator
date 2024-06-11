<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class Equipe extends Model
{
    use HasFactory;

    public static function getEquipeById($id){
        return DB::table('equipe')
            ->where('id',$id)
            ->first();
    }

    public function getAllEquipe() {
        return DB::table('equipe')
            ->get();
    }


}
