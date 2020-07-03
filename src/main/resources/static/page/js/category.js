var app = angular.module('myApp');

/**
 * @Author      : Theory
 * @Description : 登陆注册控制器
 * @type        : Controller
 */
app.controller('categoryCtrl', function ($scope, $http, $state) {


    $scope.selectLesson = null;//选择的课程


    $scope.currentPage = 1;//当前页数
    $scope.totalPage = 1; // 总页数 （根据 总记录数、每页记录数 计算 ）
    $scope.currentStatus = "all";
    $scope.currentSubject = "all";
    $scope.pages = [];



    $scope.initCategory = function () {
        $scope.selectPage(1);
    };



    //选择页数
    $scope.selectPage = function (page) {
        if ($scope.totalPage == 0 && (page < 1 || page > $scope.totalPage))
            return;
        $scope.currentPage = page;
        $scope.getLessons();
    };

    //上一页
    $scope.prevPage = function () {
        $scope.selectPage($scope.currentPage-1);
    };

    //下一页
    $scope.nextPage = function () {
        $scope.selectPage($scope.currentPage+1);
    };


    //修改状态
    $scope.setStatus = function (status) {
        $scope.currentPage = 1;
        if(status==0){
            $scope.currentStatus = "all";
        }else if(status==1){
            $scope.currentStatus = "going to start ";
        }else if(status==2){
            $scope.currentStatus = "going on now";
        }else if(status==3){
            $scope.currentStatus = "finished";
        }
        $scope.getLessons();
    };


    //修改类别
    $scope.setSubject = function (subject) {
        $scope.currentPage = 1;
        if(subject==0){
            $scope.currentSubject = "all";
        }else if(subject==1){
            $scope.currentSubject = "phylosophy";
        }else if(subject==2){
            $scope.currentSubject = "economics";
        }else if(subject==3){
            $scope.currentSubject = "law";
        }else if(subject==4){
            $scope.currentSubject = "education";
        }else if(subject==5){
            $scope.currentSubject = "literature";
        }else if(subject==6){
            $scope.currentSubject = "history";
        }else if(subject==7){
            $scope.currentSubject = "math";
        }else if(subject==8){
            $scope.currentSubject = "egineer";
        }else if(subject==9){
            $scope.currentSubject = "geography";
        }else if(subject==10){
            $scope.currentSubject = "physics";
        }else if(subject==11){
            $scope.currentSubject = "management";
        }else if(subject==12){
            $scope.currentSubject = "art";
        } else if(subject==13){
            $scope.currentSubject = "sport";
        }
        $scope.getLessons();
    };


    //发送请求
    $scope.getLessons = function () {
        var p = $scope.currentPage-1;
        $http({
            method: 'GET',
            url: "/lesson/pages?page="+p,
            params:{
                "status": $scope.currentStatus,
                "subject": $scope.currentSubject
            }
        }).then(function successCallback(response) {
            $scope.selectLesson = response.data.content;//获取返回的课程
            $scope.totalPage = response.data.totalPages;//获取最大页数
            $scope.pages = [];
            if($scope.totalPage>5) {
                var start = ($scope.currentPage>=3) ? $scope.currentPage-2 : 1;
                var end = ($scope.currentPage<=$scope.totalPage-2) ? start+4 : $scope.totalPage;
                for(var i = start;i<=end;i++)
                    $scope.pages.push(i);
            }else{
                for(var i = 1;i<=$scope.totalPage;i++)
                    $scope.pages.push(i);
            }
        });
    };


    //跳转到课程详情页面
    $scope.goDetail_2 = function (lesson) {
        window.localStorage.setItem('lessonId',lesson.lessonId);
        $state.go('courseinfo');
    }

});



