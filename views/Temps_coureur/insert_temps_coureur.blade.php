@extends('template.admin_home')

@section('import-css')
    <link href=""{{ asset('assets/libs/bootstrap/dist/css/bootstrap.min.css') }}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/libs/select2/dist/css/select2.min.css') }}">
    <link rel="stylesheet" type="text/css" href="{{ asset('assets/css/table.css') }}">
@endsection

@section('content')

    @if(session('message'))
        <div class="alert alert-success" role="alert">
            @foreach(session('message') as $message)
                {{ $message }}
            @endforeach
        </div>
    @endif

    <div class="row">
        <div class="card-body">
            <h5 class="card-title fw-semibold mb-4">Ajouter Temps_coureur </h5>
            <div class="col-md-5">
                <div class="card">
                    <div class="card-body">
                        <form action="{{ url('temps_coureur/insert') }}" method="post">
                            <div class="modal-body">
                                    @csrf
                                		<div class="mb-3">
			<label for="exampleInput_heure_depart" class="form-label">heure_depart</label>
			<input name="heure_depart" value="{{ old('heure_depart') }}" type="" class="form-control @error("heure_depart") is-invalid @enderror" id="exampleInput_heure_depart" aria-describedby="temps_coureurlHelp">
			@error("heure_depart")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_heure_arrive" class="form-label">heure_arrive</label>
			<input name="heure_arrive" value="{{ old('heure_arrive') }}" type="" class="form-control @error("heure_arrive") is-invalid @enderror" id="exampleInput_heure_arrive" aria-describedby="temps_coureurlHelp">
			@error("heure_arrive")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_penalite" class="form-label">penalite</label>
			<input name="penalite" value="{{ old('penalite') }}" type="number" class="form-control @error("penalite") is-invalid @enderror" id="exampleInput_penalite" aria-describedby="temps_coureurlHelp">
			@error("penalite")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for=\"exampleInput_id_etape class="form-label">nom</label>
			<select name="id_etape" class="select2 form-control custom-select @error('id_etape') is-invalid @enderror" style="width: 100%; height:36px;" id="exampleInput_id_etape" aria-describedby="temps_coureurlHelp">
			@foreach($ids as $id)
				 <option value="{{ $nom->id }}" @if($id_etape->id === $temps_coureur->nom) selected @endif >{{ $id->nom }}</option> 
			@endforeach
			</select>
		</div>
		<div class="mb-3">
			<label for=\"exampleInput_id_coureur class="form-label">nom</label>
			<select name="id_coureur" class="select2 form-control custom-select @error('id_coureur') is-invalid @enderror" style="width: 100%; height:36px;" id="exampleInput_id_coureur" aria-describedby="temps_coureurlHelp">
			@foreach($ids as $id)
				 <option value="{{ $nom->id }}" @if($id_coureur->id === $temps_coureur->nom) selected @endif >{{ $id->nom }}</option> 
			@endforeach
			</select>
		</div>

                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-info waves-effect">Ajouter</button>
                                <button type="button" class="btn btn-danger waves-effect" data-dismiss="modal">Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

@endsection

@section('import-js')
    <script src="{{ asset('assets/libs/select2/dist/js/select2.full.min.js') }}"></script>
    <script src="{{ asset('assets/libs/select2/dist/js/select2.min.js') }}"></script>
    <script src="{{ asset('dist/js/pages/forms/select2/select2.init.js') }}"></script>
@endsection
