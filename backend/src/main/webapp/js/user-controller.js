
app.controller('AlunoController', ['AppService', '$scope', '$location','$mdPanel', '$mdDialog', '$routeParams',
	function(AppService, $scope, $location, $mdPanel, $mdDialog, $routeParams) {

    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.editarUser = $routeParams.id != 'criar' ? true : false;

    $scope.updateList = function(){
        AppService.GetRequest('GET','users/list').then(
            function(response) {
                console.log(response);
                $scope.todos = response.data.items;
                 if($routeParams.id){
                    $scope.user = $scope.todos[$routeParams.id];
                    console.log($scope.user);
                }
            }
        );
    }
    $scope.updateList();


    $scope.showDialog = function(tag_input) {
      var position = $mdPanel.newPanelPosition()
          .absolute()
          .center();

      var config = {
        attachTo: angular.element(document.body),
        controller: function ctrl($scope) {

            $scope.saveTag = function(tag) {
                TagService.saveTag(tag).then(
                    function(response) {
                        ToastService.showToast("Tag salva.");
                        $mdPanel.close();
                    }
                );
            };
        },
        controllerAs: 'ctrl',
        disableParentScroll: this.disableParentScroll,
        templateUrl: 'static/dialog_tag.html',
        hasBackdrop: true,
        panelClass: 'demo-dialog-example',
        position: position,
        locals: {
          tag: tag_input
        },
        trapFocus: true,
        zIndex: 150,
        clickOutsideToClose: true,
        escapeToClose: true,
        focusOnOpen: true
      };

      var mdpanel = $mdPanel.open(config);
    };

    $scope.onClickTag = function(index){
        $location.path('alunos/'  + index);
        //$scope.showDialog($scope.todos[index]);
    }
   $scope.deletar = function(index){
        var user = index;
         $location.path('alunos/'  + index);
//        var confirm = $mdDialog.confirm()
//             .title('Deletar')
//             .textContent('Deseja deletar usuario ' + user.name + '?')
//             .cancel('Cancelar')
//             .ok('Sim');
//                 $mdDialog.show(confirm).then(function() {
//                  AppService.GetRequest('POST','users/create',user);
////                   TagService.deleteTag(tag).then(
////                         function(response) {
////                             $scope.updateList();
////                         }
////                     );
////                     ToastService.showToast("Tag " + tag.name+ " deletada");
//                 }, function() {});
//
//        console.log(index);
        //$scope.showDialog($scope.todos[index]);
    }
    $scope.navigate = function(path){
        console.log(path);
        $location.path(path);
    }
    $scope.criar = function(user){
        AppService.GetRequest('POST','users/create',user);
        console.log(user);
          $scope.todos =[];
         $scope.updateList();
        $location.path('alunos');

    }

}]);

app.filter('findTag', function () {

    return function (input, text) {
        if (!angular.isString(text) || text === '') {
            return input;
        }
        return input.filter(function (item) {
            return  (item.description.toLocaleLowerCase().indexOf(text.toLocaleLowerCase())!=-1) || (item.name.indexOf(text) > -1);
        });
    };

});