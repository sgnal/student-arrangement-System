$(function () {
    $("#start-game-btn").click(function () {
        //获取障碍物个数

        var zhangaiwugeshu = $("#obstacle-num").val();
        var speed = $(".speed");
        if (zhangaiwugeshu < 0 || zhangaiwugeshu > 10) {
            alert("障碍物需要在1~10之间");
            return;
        }
        if (zhangaiwugeshu === "") {
            alert("请输入障碍物");
            return;
        }
        //    获取选择的移动速度
        for (let i = 0; i < speed.length; i++) {
            if (speed[i].checked) {
                moveSpeed = speed[i].checked;
                break;
            }

        }
        $("#init-content").css("display", "none");
        $("#score-container").css("display", "block");
        $("#introduce-container").css("display", "block");
        initContainer();
        addSnake();
        for (let i = 0; i < obstacleNum; i++) {
            addObstacle();

        }

        //    添加食物
        addFood();
        //    绑定方向键事件
        document.addEventListener("keydown", onKeydownEvent, false);

    });
});
// init();
// $(function (){
//     init();
// });
//

// function init() {
//
//     var $startGamee = document.getElementById("start-game-btn");
// //    开始按钮单击事件
//     $startGamee.onclick = function () {
//         const $obstacleNum = document.getElementById('obstacle-num');
//         const $speeds = document.getElementsByName('speed');
//         //    获取障碍物个数
//         obstacleNum = $obstacleNum.value || obstacleNum;
//         if (obstacleNum < 0 || obstacleNum > 10) {
//             alert('障碍物个数需要在1~10之间')
//             return;
//         }
//         //    获取选择的移动速度
//         for (let i = 0; i < $speeds.length; i++) {
//             if ($speeds[i].checked) {
//                 moveSpeed = $speeds[i].value;
//                 break;
//             }
//
//         }
//         document.getElementById("#init-content").style.display = "none";
//         document.getElementById("#score-container").style.display = "block";
//         document.getElementById("#introduce-container").style.display="block";
//         initContainer();
//         for (let i = 0; i < obstacleNum; i++) {
//             addObstacle();
//
//
//         }
//     //    添加食物
//         addFood();
//     //    绑定方向键事件
//         document.addEventListener('keydown',onKeydownEvent,false);
//
//     }
//
//
// }
// function xiaoshi(){
//     document.getElementById("score-container").style.display = "none";
//
// }
<!--    设置障碍物个数-->
let obstacleNum = 3;
let moveSpeed = 300;
//小蛇的初始位置
let initPosition = [];
//小蛇所有不为的数组
let snakePositions = [];
//整个小蛇移动格子个数
const size = 20;
//每个格子的大小
const vertical = 20;

//障碍物个数
let obstaclePositions = [];
//事务出现的定时器
let foodTimer = null;
// 食物是否已经存在
let foodExist = false;
//食物位置
let foodPosition = [];
//小蛇移动定时器
let snakeTimer = null;


//方向键对应事件code
const KEY_LEFT_CODE = 37;
const KEY_UP_CODE = 38;
const KEY_RIGHT_CODE = 39;
const KEY_DOWN_CODE = 40;

//所有游戏状态
const stateEnum = {
    INIT: 0,
    DOING: 1,
    END: 2
}

const targetEnum = {
    LEFT: 'left',
    UP: 'up',
    RIGHT: 'right',
    DOWN: 'down'
}
//小蛇移动方向
let target = null;
//当前游戏状态
let state = stateEnum.INIT;

//记录上一次小蛇移动位置的数组
let oldSnakePositions = [];

//每次吃到食物的加分
const foodScore = 100;

//当前游戏得分
let score = 0;


function getElemByClass(className) {
    return document.querySelector('.' + className);

}

function getElemsByClass(className) {
    return document.querySelectorAll('.' + className);
}

function getElemById(id) {
    return document.getElementById(id);
}

function getElemsByName(name) {
    return document.getElementsByName(name);
}


//添加小蛇
function addSnake() {
    //    计算容器中间位置
    const center = Math.floor(size / 2);
    //    设置小蛇初始位置为容器中间
    initPosition = [center, center];
    //    设置小蛇的位置
    snakePositions = [[...initPosition]];
    //    小蛇的DOM位置
    const $snake = '<span class="snake-item" id="snake-item" style="top: ' + getTopPosition(initPosition[1]) + ';left:' + getLeftPosition(initPosition[0]) + '"></span>';
    //    添加小蛇到容器中
    getElemById("container").innerHTML = '<span class="snake">' + $snake + '</span>';


}

function getLeftPosition(x) {
    return x * vertical + 'px';
}

function getTopPosition(y) {
    return y * vertical + 'px';
}


//    初始化小蛇移动界面
function initContainer() {
    const containerWidth = size * vertical + 'px';
    const containerHeight = containerWidth;
    const $container = getElemById("container");
    //

    $container.style.display = "block";
    $container.style.width = containerWidth;
    $container.style.height = containerHeight;
}

//    添加障碍物
function addObstacle() {
    //    随机生成障碍物的位置
    const position = getRandomPosition();
    const x = position[0];
    const y = position[1];
    //    如果生成障碍物的位置存在小蛇或者障碍物，重新生成障碍物
    if (isSnakePosition(x, y) || isObstaclePosition(x, y)) {
        return addObstacle();

    }

    //    将障碍物添加到界面中
    obstaclePositions.push([x, y]);
    const $obstacle = document.createElement("span");
    $obstacle.setAttribute("class", "obstacle");
    $obstacle.style.left = x * vertical + 'px';
    $obstacle.style.top = y * vertical + 'px';
    getElemById("container").appendChild($obstacle);
    // $("container").appendChild($obstacle);


}

//在界面上，随机生成一个位置
function getRandomPosition() {
    let x = Math.round(Math.random() * size);
    let y = Math.round(Math.random() * size);
    x = x >= size ? size - 1 : x;
    y = y >= size ? size - 1 : y;
    return [x, y]
}

//    判断界面上某个位置是否存在小蛇的身体
function isSnakePosition(x, y) {
    for (let i = 0; i < snakePositions.length; i++) {
        if (snakePositions[i][0] === x && snakePositions[i][1] === y) {
            return true;

        }

    }
    return false;
}

//    判断界面上某个位置是否存在障碍物
function isObstaclePosition(x, y) {
    for (let i = 0; i < obstaclePositions.length; i++) {
        if (obstaclePositions[i][0] === x && obstaclePositions[i][1] === y) {
            return true;

        }

    }
    return false;

}

//    添加食物
function addFood() {
    showFood();
    //    开启定时器随机出现的食物
    foodTimer = setInterval(function () {
        showFood();
    }, moveSpeed);
}

//随机出现食物
function showFood() {
    //    如果存在食物或者游戏结束，直接返回
    if (foodExist || state === stateEnum.END) {
        return;
    }
    //    随机生成事物的位置
    const position = getRandomPosition();
    const x = position[0];
    const y = position[1];
    //    如果出现事物的位置存在小蛇后者障碍物，重新生成食物
    if (isSnakePosition(x, y) || isObstaclePosition(x, y)) {

        return showFood();
    }
    foodPosition = [x, y];
    const $food = document.createElement("span");
    $food.setAttribute("class", "food");
    $food.style.left = x * vertical + 'px';
    $food.style.top = y * vertical + 'px';
    getElemById("container").appendChild($food);
    foodExist = true;
}

//方向键事件
function onKeydownEvent(e) {
    //  当游戏结束，直接返回
    if (state === stateEnum.INIT) {
        return
    }
    // 游戏初始状态开始移动方向后，将状态设置为运行状态
    if (state === stateEnum.INIT) {
        state = stateEnum.DOING;
    }

    //    根据不同按键方向进行移动
    switch (e.keyCode) {
        case KEY_LEFT_CODE:
            moveLeft();
            break;
        case KEY_UP_CODE:
            moveUp();
            break;
        case KEY_RIGHT_CODE:
            moveRight();
            break;
        case KEY_DOWN_CODE:
            moveDown();
            break;

    }

}

function moveLeft() {
    //    如果小蛇本身在x轴移动，则不允许调整方向
    if (target === targetEnum.LEFT || target === targetEnum.RIGHT) {
        return;
    }
    //    将方向设置为向左移动
    target = targetEnum.LEFT;
    const $firstSnakeItem = getElemByClass("snake-item");
    //    旋转小蛇头部的方向到初始位置
    $firstSnakeItem.style.transform = 'rotate(0)';
    //    调用moveSnake()调整方向，并移动小蛇
    moveSnake();

}

function moveRight() {
    //    如果小蛇本身在x轴移动，则不允许调整方向
    if (target === targetEnum.RIGHT || target === targetEnum.LEFT) {
        return;
    }
    //    将方向设置为向左移动
    target = targetEnum.RIGHT;
    const $firstSnakeItem = getElemByClass("snake-item");
    //    旋转小蛇头部的方向到初始位置
    $firstSnakeItem.style.transform = 'rotate(180)';
    //    调用moveSnake()调整方向，并移动小蛇
    moveSnake();
}

function moveDown() {
    //    小蛇如果本身在y轴移动，则不允许调整方向
    if (target === targetEnum.DOWN || target === targetEnum.UP) {
        return;
    }
    //    将方向设置为向上运动
    target = targetEnum.DOWN;
    const $firstSnakeItem = getElemByClass("snake-item");
    //    旋转小蛇头部的方向到90度
    $firstSnakeItem.style.transform = 'rotate(-90deg)';
    //    调用moveSnake()调整方向，并移动小蛇
    moveSnake();
}

function moveUp() {
    //    小蛇如果本身在y轴移动，则不允许调整方向
    if (target === targetEnum.UP || target === targetEnum.DOWN) {
        return;
    }
    //    将方向设置为向上运动
    target = targetEnum.UP;
    const $firstSnakeItem = getElemByClass("snake-item");
    //    旋转小蛇头部的方向到90度
    $firstSnakeItem.style.transform = 'rotate(90deg)';
    //    调用moveSnake()调整方向，并移动小蛇
    moveSnake();
}

function moveSnake() {
    clearInterval(snakeTimer);
    movePosition();
    snakeTimer = setInterval(function () {
        movePosition();

    }, moveSpeed)
}

function movePosition() {
    if (!target || state === stateEnum.END) {
        return;
    }

    const newSnakePositions = getNewSnakePositions();
    oldSnakePositions = [...snakePositions];
    snakePositions = [...newSnakePositions];
    const result = getResult();
    if (result === stateEnum.END) {
        showGameOver();
        return;
    }
    const $snakeItems = getElemsByClass("snake-item");
    [...$snakeItems].map(($snakeItem, index) => {
        $snakeItems.style.transition = `all ${moveSpeed}ms linear`;
        $snakeItem.style.left = getLeftPosition(snakePositions[index][0]);
        $snakeItem.style.top = getTopPosition(snakePositions[index][1]);
    });

}

function getNewSnakePositions() {
    const newSnakePositions = [];
    switch (target) {
        case targetEnum.UP:
            newSnakePositions.push([snakePositions[0][0], snakePositions[0][1] - 1]);
            break;
        case targetEnum.RIGHT:
            newSnakePositions.push([snakePositions[0][0] + 1, snakePositions[0][1]]);
            break;
        case targetEnum.DOWN:
            newSnakePositions.push([snakePositions[0][0], snakePositions[0][1] + 1]);
            break;
        case targetEnum.left:
            newSnakePositions.push([snakePositions[0][0] - 1, snakePositions[0][1]]);
            break;

    }
    for (let i = 0; i < snakePositions.length; i++) {
        const preSnakePosition = snakePositions[i - 1];
        newSnakePositions.push(preSnakePosition);


    }
    return newSnakePositions;


}

function getResult() {
    if (snakePositions[0][0] < 0 || snakePositions[0][0] > size - 1 || snakePositions[0][1] < 0 || snakePositions[0][1] > size - 1) {
        state = stateEnum.END;
        return state;

    }
    const isHitObstacle = obstaclePositions.some(obstaclePosition => {
        if (obstaclePosition[0] === snakePositions[0][0] && obstaclePosition[1] === snakePositions[0][1]) {
            return true;
        }
    });
    if (isHitObstacle) {
        state = stateEnum.END;
        return state;

    }
    const isHitSelf = oldSnakePositions.some(snakePosition => {
        if (snakePositions[0][0] === snakePosition[0] && snakePositions[0][1] === snakePosition[1]) {
            return true;
        }
    });
    if (isHitSelf) {
        state = stateEnum.END;
        return state;


    }
    if (eatFood()) {
        strongSnake();
        foodExist = false;
        const $food = getElemByClass("food");
        getElemById("container").removeChild($food);
        //    小蛇的分数加100，并更新在界面上
        score += foodScore;
        getElemById("score").innerHTML = score;

    }
    state = stateEnum.DOING
    return this.state;
}

function eatFood() {
    if (snakePositions[0][0] === foodPosition[0] && snakePositions[0][1] === foodPosition[1]) {
        return true;
    }
    return;
}

function strongSnake() {
    let snakeItemPosition = [];
    const length = oldSnakePositions.length;
    snakeItemPosition = [oldSnakePositions[length - 1][0], oldSnakePositions[length - 1][1]];
    snakePositions.push(snakeItemPosition);
    const $snakeItem = document.createElement("span");
    $snakeItem.setAttribute("class", "snake-item");
    $snakeItem.style.left = getLeftPosition(snakeItemPosition[0]);
    $snakeItem.style.top = getTopPosition(snakeItemPosition[1]);
    getElemByClass("snake").appendChild($snakeItem);
}

function showGameOver() {
    clearInterval(snakeTimer);
    clearInterval(foodTimer);
    snakeTimer = null;
    foodTimer = null;
    const $gameOverContainer = document.getElementById("game-over-container");
    $gameOverContainer.style.display = "block";
    getElemById("total-score").innerHTML = score;
    getElemById("restart-btn").onclick = function () {
        getElemById("game-over-container").style.display = 'none';
        resetInitData();

    }
    getElemById("exit-btn").onclick = function () {
        window.location.reload();
    }
}

function resetInitData() {
    state = stateEnum.INIT;
    snakePositions = [];
    oldSnakePositions = [];
    obstaclePositions = [];
    foodPosition = [];
    target = null;
    foodExist = false;
    score = 0;
    document.getElementById("score").innerHTML = 0;
    document.removeEventListener("keydown", onKeydownEvent);

}


