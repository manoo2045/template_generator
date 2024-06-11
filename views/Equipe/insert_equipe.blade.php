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
            <h5 class="card-title fw-semibold mb-4">Ajouter Equipe </h5>
            <div class="col-md-5">
                <div class="card">
                    <div class="card-body">
                        <form action="{{ url('equipe/insert') }}" method="post">
                            <div class="modal-body">
                                    @csrf
                                		<div class="mb-3">
			<label for="exampleInput_nom" class="form-label">nom</label>
			<input name="nom" value="{{ old('nom') }}" type="text" class="form-control @error("nom") is-invalid @enderror" id="exampleInput_nom" aria-describedby="equipelHelp">
			@error("nom")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_pwd" class="form-label">pwd</label>
			<input name="pwd" value="{{ old('pwd') }}" type="text" class="form-control @error("pwd") is-invalid @enderror" id="exampleInput_pwd" aria-describedby="equipelHelp">
			@error("pwd")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_login" class="form-label">login</label>
			<input name="login" value="{{ old('login') }}" type="text" class="form-control @error("login") is-invalid @enderror" id="exampleInput_login" aria-describedby="equipelHelp">
			@error("login")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
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
