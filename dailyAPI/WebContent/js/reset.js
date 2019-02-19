// 动态设置视口大小
var iScale = window.devicePixelRatio;
iScale = 1/iScale;
document.write('<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale='+ iScale +', maximum-scale='+ iScale +', minimum-scale='+ iScale +'" />');

// 设置 rem 默认大小
var iWidth = document.documentElement.clientWidth;
var iFont = iWidth/10;

document.getElementsByTagName('html')[0].style.fontSize = iFont+'px';