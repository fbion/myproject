/***
 * ҳ����ʼ������
 */
jQuery(function () {
    //�����˵�
    if(jQuery('form>input[type=hidden][name=key]').val()=='sjzf'){    //�ֻ�֧���˻�
        jQuery('#CZJF_SJZFZH').addClass('active');
    }else{
        jQuery('#CZJF_YHKCZ').addClass('active');
    }

   //默认银行
//    var lastBank = jQuery.cookie('lastBank');
//    if (lastBank == "" || jQuery('#' + lastBank).val() == undefined) {  //û�м�¼�������Ҳ�����Ӧ����
//        jQuery('input[type=radio][id=YHKJF_YRCZ_SJZF]').attr('checked', true);
//    } else {
//        jQuery('input[type=radio][name=pay_bank_id][id=' + lastBank + ']').attr('checked', true);
//        /***
//         * ��ʾ����֧�����ÿ�֧��
//         */
//        if (lastBank != undefined && lastBank.startsWith("YHKJF_YRCZ_KJZF_XYK") == true) {
//
//            jQuery('#KJZF_TAB_DIV').find('li').each(function (i) {
//                if (i == 0) {
//                    jQuery(this).removeClass('cur');
//                } else if (i == 1) {
//                    jQuery(this).addClass('cur');
//                }
//            });
//            jQuery('#kjzf_cxk_ul').hide();
//            jQuery('#kjzf_xyk_ul').show();
//        }
//    }
});
/***
 * �����޸�
 */
function btnGobakck() {
    document.forms[0].target = "";
    document.forms[0].nextMethod.value = 'pageInit';
    document.forms[0].submit();
//    __doPostBack('pageInit', '', '');
}
/***
 * ����֧��
 */
function btnSubmit() {
//    alert(1111);
//     alert( jQuery('input[type=radio][name=userHongBaoFlag]:checked').val())     ;
//     var userHongBaoFlag = jQuery('input[type=radio][name=userHongBaoFlag]:checked').val();
//
//    alert(userHongBaoFlag);
//        return false;

    var bank = jQuery('input[type=radio][name=pay_bank_id]:checked').val();
//    if (bank == null || bank == undefined) {
//        alert('��ѡ��֧����ʽ');
//        return false;
//    }
    /***
     * ����֧����ť����
     */
    buttonToGray();
    /***
     * �����ڸǲ�
     * @type {*}
     */
//    jQuery.blockUI({
//        message: jQuery('#div_zhifu'),
//        css: {left: '20%', top: '20%', cursor: 'default' }
//    });
    var id = jQuery('input[type=radio][name=pay_bank_id]:checked').attr('id');
    //jQuery.cookie('lastBank', id, { expires: 365 });
    document.forms[0].target = "_blank";
    //document.forms[0].nextMethod.value = 'btnGoPay';
    document.forms[0].submit();
//    __doPostBack('btnGoPay', '', '');
}
/***
 * �رյ�¼����
 */
function closeWindow() {
    jQuery.unblockUI();
    //�ָ���ť��ʽ
    var obj = jQuery('img[id="YHKJF_YRCZ_LJZF"]');
    obj.attr('disabled', false);
    obj.css('filter', '');
    obj.css('cursor', 'pointer');
}
/***
 * ������֧��
 */
function completePayment() {
    window.location = root + "/internetFee_new/jsp/payment/payment_third.jsp?order_info=" + order_info;

}

/***
 *  ��ť���Ҳ�������
 */
function buttonToGray() {
    //���г�ֵ�ɷ�
    var obj = jQuery('img[id="YHKJF_YRCZ_LJZF"]');
    obj.attr('disabled', true);
    obj.css('filter', 'gray');
    obj.css('cursor', '');
}

/***
 * ����֧��ҳǩ�л�
 */
function btnKjzfTabChange() {
    jQuery('#KJZF_TAB_DIV').find('li').toggleClass('cur');
    jQuery('ul[class=pay_list_con][id*=kjzf_]').toggle().each(function (i) {
        if (jQuery(this).is(':hidden')) {
            jQuery(this).find('input[type=radio]').each(function (j) {
                jQuery(this).attr('checked', false);
            });
        }
    });

}