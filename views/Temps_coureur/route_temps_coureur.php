Route::get('/temps_coureur',[Temps_coureurController::class,'list']);
Route::get('/temps_coureur/list/',[Temps_coureurController::class,'list']);
Route::post('/temps_coureur/insert',[Temps_coureurController::class,'insert']);
//Route::get('/temps_coureur/insert',[Temps_coureurController::class,'temps_coureurInsertView']);
Route::get('/temps_coureur/edit/{id}',[Temps_coureurController::class,'temps_coureurEditView']);
Route::post('/temps_coureur/update',[Temps_coureurController::class,'temps_coureurEdit']);
Route::get('/temps_coureur/delete/{id}',[Temps_coureurController::class,'delete']);


<li class="sidebar-item">
    <a href="{{ url('/temps_coureur/list') }}" class="sidebar-link">
        <i class="mdi mdi-av-timer"></i>
        <span class="hide-menu">temps_coureur</span>
    </a>
</li>

