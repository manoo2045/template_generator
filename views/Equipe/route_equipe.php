Route::get('/equipe',[EquipeController::class,'list']);
Route::get('/equipe/list/',[EquipeController::class,'list']);
Route::post('/equipe/insert',[EquipeController::class,'insert']);
Route::get('/equipe/edit/{id}',[EquipeController::class,'equipeEditView']);
//Route::get('/equipe/insert',[EquipeController::class,'equipeInsertView']);
Route::post('/equipe/update',[EquipeController::class,'equipeEdit']);
Route::get('/equipe/delete/{id}',[EquipeController::class,'delete']);


<li class="sidebar-item">
    <a href="{{ url('/equipe/list') }}" class="sidebar-link">
        <i class="mdi mdi-av-timer"></i>
        <span class="hide-menu">equipe</span>
    </a>
</li>

