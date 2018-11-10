
app.controller('AlunoController', ['AppService', 'TagService', 'ToastService', '$scope', '$location','$mdPanel', '$mdDialog',
	function(AppService, TagService, ToastService, $scope, $location, $mdPanel, $mdDialog) {
    alert('dasdas');
    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.updateList = function(){
        TagService.listTags().then(
            function(response) {
                $scope.todos = response.data;
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

    $scope.deletar = function(tag){
        var confirm = $mdDialog.confirm()
          .title('Deletar')
          .textContent('Deseja deletar a tag: ' + tag.description)
          .cancel('Cancelar')
          .ok('Sim');

        $mdDialog.show(confirm).then(function() {
          TagService.deleteTag(tag).then(
                function(response) {
                    $scope.updateList();
                }
            );
            ToastService.showToast("Tag " + tag.name+ " deletada");
        }, function() {});
    }

    $scope.onClickTag = function(index){
        $scope.showDialog($scope.todos[index]);
    }
    $scope.navigate = function(path){
        alert(path);
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
app.controller('AlunoController', ['AppService', 'TagService', 'ToastService', '$scope', '$location','$mdPanel', '$mdDialog',
	function(AppService, TagService, ToastService, $scope, $location, $mdPanel, $mdDialog) {

    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.updateList = function(){
        TagService.listTags().then(
            function(response) {
                $scope.todos = response.data;
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

    $scope.deletar = function(tag){
        var confirm = $mdDialog.confirm()
          .title('Deletar')
          .textContent('Deseja deletar a tag: ' + tag.description)
          .cancel('Cancelar')
          .ok('Sim');

        $mdDialog.show(confirm).then(function() {
          TagService.deleteTag(tag).then(
                function(response) {
                    $scope.updateList();
                }
            );
            ToastService.showToast("Tag " + tag.name+ " deletada");
        }, function() {});
    }

    $scope.onClickTag = function(index){
        $scope.showDialog($scope.todos[index]);
    }
    $scope.navigate = function(path){
        alert(path);
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
app.controller('AlunoController', ['AppService', 'TagService', 'ToastService', '$scope', '$location','$mdPanel', '$mdDialog',
	function(AppService, TagService, ToastService, $scope, $location, $mdPanel, $mdDialog) {

    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.updateList = function(){
        TagService.listTags().then(
            function(response) {
                $scope.todos = response.data;
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

    $scope.deletar = function(tag){
        var confirm = $mdDialog.confirm()
          .title('Deletar')
          .textContent('Deseja deletar a tag: ' + tag.description)
          .cancel('Cancelar')
          .ok('Sim');

        $mdDialog.show(confirm).then(function() {
          TagService.deleteTag(tag).then(
                function(response) {
                    $scope.updateList();
                }
            );
            ToastService.showToast("Tag " + tag.name+ " deletada");
        }, function() {});
    }

    $scope.onClickTag = function(index){
        $scope.showDialog($scope.todos[index]);
    }
    $scope.navigate = function(path){
        alert(path);
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
app.controller('AlunoController', ['AppService', 'TagService', 'ToastService', '$scope', '$location','$mdPanel', '$mdDialog',
	function(AppService, TagService, ToastService, $scope, $location, $mdPanel, $mdDialog) {

    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.updateList = function(){
        TagService.listTags().then(
            function(response) {
                $scope.todos = response.data;
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

    $scope.deletar = function(tag){
        var confirm = $mdDialog.confirm()
          .title('Deletar')
          .textContent('Deseja deletar a tag: ' + tag.description)
          .cancel('Cancelar')
          .ok('Sim');

        $mdDialog.show(confirm).then(function() {
          TagService.deleteTag(tag).then(
                function(response) {
                    $scope.updateList();
                }
            );
            ToastService.showToast("Tag " + tag.name+ " deletada");
        }, function() {});
    }

    $scope.onClickTag = function(index){
        $scope.showDialog($scope.todos[index]);
    }
    $scope.navigate = function(path){
        alert(path);
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
app.controller('AlunoController', ['AppService', 'TagService', 'ToastService', '$scope', '$location','$mdPanel', '$mdDialog',
	function(AppService, TagService, ToastService, $scope, $location, $mdPanel, $mdDialog) {

    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.updateList = function(){
        TagService.listTags().then(
            function(response) {
                $scope.todos = response.data;
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

    $scope.deletar = function(tag){
        var confirm = $mdDialog.confirm()
          .title('Deletar')
          .textContent('Deseja deletar a tag: ' + tag.description)
          .cancel('Cancelar')
          .ok('Sim');

        $mdDialog.show(confirm).then(function() {
          TagService.deleteTag(tag).then(
                function(response) {
                    $scope.updateList();
                }
            );
            ToastService.showToast("Tag " + tag.name+ " deletada");
        }, function() {});
    }

    $scope.onClickTag = function(index){
        $scope.showDialog($scope.todos[index]);
    }
    $scope.navigate = function(path){
        alert(path);
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
app.controller('AlunoController', ['AppService', 'TagService', 'ToastService', '$scope', '$location','$mdPanel', '$mdDialog',
	function(AppService, TagService, ToastService, $scope, $location, $mdPanel, $mdDialog) {

    // AppService.GetRequest('POST','/test','dasdas').then(function(resp){
    // 	console.log(resp);
    // });
    $scope.updateList = function(){
        TagService.listTags().then(
            function(response) {
                $scope.todos = response.data;
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

    $scope.deletar = function(tag){
        var confirm = $mdDialog.confirm()
          .title('Deletar')
          .textContent('Deseja deletar a tag: ' + tag.description)
          .cancel('Cancelar')
          .ok('Sim');

        $mdDialog.show(confirm).then(function() {
          TagService.deleteTag(tag).then(
                function(response) {
                    $scope.updateList();
                }
            );
            ToastService.showToast("Tag " + tag.name+ " deletada");
        }, function() {});
    }

    $scope.onClickTag = function(index){
        $scope.showDialog($scope.todos[index]);
    }
    $scope.navigate = function(path){
        alert(path);
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