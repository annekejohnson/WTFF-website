var topic = document.getElementsByClassName("topic");
var FAQ = document.getElementsByClassName("FAQ");
var question = document.getElementsByClassName("question");
var answer = document.getElementsByClassName("answer");
var plus = document.getElementsByClassName("plus");
var minus = document.getElementsByClassName("minus");

for (var i = 0; i < FAQ.length; i++){
    FAQ[i].style.display = "none";
}
for (var i = 0; i < minus.length; i++){
    minus[i].style.display = "none";
}
function displayQuestion(index){
    if (FAQ[index].style.display === "none"){
        FAQ[index].style.display = "block";
    } else {
        FAQ[index].style.display = "none";
    }
}

for (var i = 0; i < answer.length; i++){
    answer[i].style.display = "none";
}

for (var i = 0; i < question.length; i++) {
    question[i].addEventListener("click", function(event) {
        var index = Array.from(question).indexOf(event.target);
        displayAnswer(index);
    });
}

function displayAnswer(index) {
    if (answer[index].style.display === "none") {
        answer[index].style.display = "block";
        plus[index].style.display = "none";
        minus[index].style.display = "block";
    } else {
        answer[index].style.display = "none";
        plus[index].style.display = "block";
        minus[index].style.display = "none";
    }
}