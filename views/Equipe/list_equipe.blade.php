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
        <div id="exampleModal" class="modal fade in" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel"> Ajouter Equipe </h4>
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    </div>
                    <form action="{{ url('equipe/insert') }}" method="post">
                        <div class="modal-body">
                                @csrf
                            		<div class="mb-3">
			<label for="exampleInput_nom" class="form-label">nom</label>
			<input name="nom" value="{{ old('nom') }}" type="text" class="form-control @error("nom") is-invalid @enderror" id="exampleInput_nom" aria-describedby="#CLASS_NAME#lHelp">
			@error("nom")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_pwd" class="form-label">pwd</label>
			<input name="pwd" value="{{ old('pwd') }}" type="text" class="form-control @error("pwd") is-invalid @enderror" id="exampleInput_pwd" aria-describedby="#CLASS_NAME#lHelp">
			@error("pwd")
				<div class="invalid-feedback">
					{{ $message }}
				</div>
			@enderror
		</div>

		<div class="mb-3">
			<label for="exampleInput_login" class="form-label">login</label>
			<input name="login" value="{{ old('login') }}" type="text" class="form-control @error("login") is-invalid @enderror" id="exampleInput_login" aria-describedby="#CLASS_NAME#lHelp">
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
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>

        <div class="col-md-10">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title">Liste  equipes </h4>
                        <div class="">
                            <table id="demo-foo-addrow" class="content-table col-md-12" >
                                <thead>
                                    <tr>
									<th class="footable-sortable"> nom <th>
									<th class="footable-sortable"> pwd <th>
									<th class="footable-sortable"> login <th>

                                    <th class="footable-sortable">  <th>
                                    </tr>
                                </thead>
                                <tbody>
                                @foreach($equipes as $equipe)
                                    <tr class="footable-even" style="">
                                        
									<td class="footable-sortable"> {{ $equipe->nom }} <td>
									<td class="footable-sortable"> {{ $equipe->pwd }} <td>
									<td class="footable-sortable"> {{ $equipe->login }} <td>

                                        <td>
                                            <div class="btn-group btn-group-sm" role="group" aria-label="Basic example">
                                                <a href="{{ url('equipe/edit/'.$equipe->id) }}" type="button" class="btn btn-info"><i class="fa fa-edit"></i></a>
                                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal" data-whatever="@getbootstrap"><i class="fa fa-trash"></i></button>
                                            </div>
                                        </td>
                                    </tr>
                                    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel1">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h4 class="modal-title" id="myModalLabel">Modal Heading</h4>
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                </div>
                                                <div class="modal-body">
                                                    <h4>Overflowing text to show scroll behavior</h4>
                                                    <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <a href="{{ url('equipe/delete/'.$equipe->id) }}" type="button" class="btn btn-danger">Oui</a>
                                                    <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal">Cancel</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                @endforeach
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="7">

                                    </td>
                                </tr>
                                </tfoot>
                            </table>
{{--                            <div class="">--}}
{{--                                <nav aria-label="Page navigation example">--}}
{{--                                    {{ $equipes->links('pagination::bootstrap-5') }}--}}
{{--                                </nav>--}}
{{--                            </div>--}}

                            <div class="ml-2">
                                <button type="button" class="btn waves-effect waves-light btn-rounded btn-info mb-4" onclick="openModal()">Ajouter</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="{{ asset('assets/libs/jquery/dist/jquery.min.js') }}"></script>
    <script>
        function openModal() {
            $('#exampleModal').modal('show');
        }
    </script>

    @if(session('errors'))
        <script>
            $(document).ready(function () {
                $('#exampleModal').modal('show');
            });
        </script>
    @endif
@endsection

@section('import-js')
    <script src="{{ asset('assets/libs/select2/dist/js/select2.full.min.js') }}"></script>
    <script src="{{ asset('assets/libs/select2/dist/js/select2.min.js') }}"></script>
    <script src="{{ asset('dist/js/pages/forms/select2/select2.init.js') }}"></script>
@endsection
