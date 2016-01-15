var d = document;
var safari = (navigator.userAgent.toLowerCase().indexOf('safari') != -1) ? true : false;
 
var gebtn = function (parEl, child) { return parEl.getElementsByTagName(child); };
onload = function () {
    var body = gebtn(d, 'body')[0];
    body.className = body.className && body.className != '' ? body.className + ' has-js' : 'has-js';
    if (!d.getElementById || !d.createTextNode) {
        return;
    }
    var ls = gebtn(d, 'label');
    for (var i = 0; i < ls.length; i++) {
        var l = ls[i];
        if (l.className.indexOf('label_') == -1) {
            continue;
        }
        //debugger;
        var inp = gebtn(l, 'input')[0];
        if (l.className == 'label_check') {
            l.className = (safari && inp.checked == true || inp.checked) ? 'label_check c_on' : 'label_check c_off';
            //l.className = (safari && inp.checked == true || inp.checked) ? 'label_check c_off' : 'label_check c_on';
            l.onclick = check_it;
            inp.onclick = function (e) { //阻止冒泡,避免label点击事件多触发一次
                e && e.stopPropagation ? e.stopPropagation() : window.event.cancelBubble = true;
            }
        };
        if (l.className == 'label_radio') {
            l.className = (safari && inp.checked == true || inp.checked) ? 'label_radio r_on' : 'label_radio r_off';
            l.onclick = turn_radio;
        };
    };
};
var check_it = function (e) {
    var inp = gebtn(this, 'input')[0];
    if (this.className == 'label_check c_off' || (!safari && inp.checked)) {
        this.className = 'label_check c_on';
        if (safari) {
            //inp.click();
            inp.checked = false;
            //console.log(document.getElementById('mytest').checked);
        };
    } else {
        this.className = 'label_check c_off';
        if (safari) {
            //inp.click();
            inp.checked = true; //也会触发事件冒泡
            //inp.setAttribute('checked', false);//不会事件冒泡，但是设置为true时无效。
            //console.log(inp);
            //console.log(this);
        };
    };
};
var turn_radio = function () {
    var inp = gebtn(this, 'input')[0];
    if (this.className == 'label_radio r_off' || inp.checked) {
        var ls = gebtn(this.parentNode, 'label');
        for (var i = 0; i < ls.length; i++) {
            var l = ls[i];
            if (l.className.indexOf('label_radio') == -1) {
                continue;
            };
            l.className = 'label_radio r_off';
        };
        this.className = 'label_radio r_on';
        if (safari) {
            inp.click();
        };
    } else {
        this.className = 'label_radio r_off';
        if (safari) {
            inp.click();
        };
    };
}; 