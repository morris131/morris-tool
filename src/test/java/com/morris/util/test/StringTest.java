package com.morris.util.test;

import org.junit.Test;

public class StringTest {
	
	@Test
	public void test() {
		String sqlString ="select a.ORDER_NO,\n" + 
				"       A.OUT_ORDER_NO,\n" + 
				"       A.PNR,\n" + 
				"       A.BIG_PNR,\n" + 
				"       A.ORDER_DATE,\n" + 
				"       A.FLY_TYPE_DID,\n" + 
				"       A.ORDER_PSGR_TYPE_DID,\n" + 
				"       a.salechnl_pay_mins,\n" + 
				"       A.ORDER_TYPE_DID,\n" + 
				"       A.ORDER_STAT_DID,\n" + 
				"       A.IS_PAIED,\n" + 
				"       A.ORDER_PLAT_STAT_DID,\n" + 
				"       A.AUTOTICKET_TASK_STAT_DID,\n" + 
				"       A.CHARGE_FEE,\n" + 
				"       A.receipt_type_did,\n" + 
				"       A.ORDER_SALECHNL_CODE,\n" + 
				"       A.POLICY_TYPE_DID,\n" + 
				"       A.SUPPLIER_ID,\n" + 
				"       A.TICKET_TYPE_DCD,\n" + 
				"       A.ticket_soft_did,\n" + 
				"       A.ORDER_SRC_DID,\n" + 
				"       A.ORDER_OFFICE_NO,\n" + 
				"       A.TICKET_DATE,\n" + 
				"       A.purchase_pay_amount,\n" + 
				"       (CASE\n" + 
				"         WHEN a.order_stat_did in (1, 2, 4, 12) THEN\n" + 
				"          (sysdate - a.order_date) * 24 * 60 * 60\n" + 
				"         else\n" + 
				"          a.ticket_spent_secs\n" + 
				"       end) as ticket_spent_secs,\n" + 
				"       (CASE\n" + 
				"         WHEN a.order_stat_did in (5) THEN\n" + 
				"          (a.LAST_UPD_DATE - a.order_date) * 24 * 60 * 60\n" + 
				"         else\n" + 
				"          a.ticket_spent_secs\n" + 
				"       end) as rejectDate,\n" + 
				"       A.REAL_PAY_AMOUNT,\n" + 
				"       A.DEAL_PAY_AMOUNT,\n" + 
				"       A.ORIG_PNR,\n" + 
				"       A.ORIG_BIG_PNR,\n" + 
				"       A.APPLY_REMARK,\n" + 
				"       A.RETURN_REMARK,\n" + 
				"       A.CORP_NO,\n" + 
				"       A.TICKET_AUTO_TYPE_DID,\n" + 
				"       A.TICKET_USER_ID,\n" + 
				"       A.IS_LOCK,\n" + 
				"       A.IS_ALLOW_EXCHANGE,\n" + 
				"       A.NOTIFY_STAT_DID,\n" + 
				"       A.ORIG_ORDER_NO,\n" + 
				"       A.REJECT_REASON,\n" + 
				"       A.AUDITOR_ID,\n" + 
				"       A.AUDIT_DATE,\n" + 
				"       A.IS_APPROVED,\n" + 
				"       A.AUDIT_DESC,\n" + 
				"       A.CABIN_DESC,\n" + 
				"       A.CONTACT_NAME,\n" + 
				"       A.CONTACT_TEL,\n" + 
				"       A.IS_TEAM,\n" + 
				"       A.IS_ROUND_DISCOUNT,\n" + 
				"       A.SUPPLIER_WORK_TIME,\n" + 
				"       A.SUPPLIER_CANCEL_TIME,\n" + 
				"       A.CREATOR_ID,\n" + 
				"       A.CREATE_DATE,\n" + 
				"       A.is_central_dept_time,\n" + 
				"       A.LAST_UPD_USER_ID,\n" + 
				"       A.LAST_UPD_DATE,\n" + 
				"       a.outer_source,\n" + 
				"       a.gsspay_type,\n" + 
				"       a.outer_source_name,\n" + 
				"       A.THIRD_HANDLE_SN,\n" + 
				"       E.TICKET_NO,\n" + 
				"       E.SALE_REBATE,\n" + 
				"       E.SALE_POLICY_SALED_ID,\n" + 
				"       E.TICKET_POLICY_SALED_ID,\n" + 
				"       E.EXTRA_ID,\n" + 
				"       E.TICKET_ORIG_REBATE,\n" + 
				"       E.TICKET_MAJOR_REBATE,\n" + 
				"       E.BEST_POLICY_SALED_ID,\n" + 
				"       E.BEST_ORIG_REBATE,\n" + 
				"       E.BEST_MAJOR_REBATE,\n" + 
				"       E.TICKET_FARE,\n" + 
				"       E.FUEL_FEE,\n" + 
				"       E.AIRPORT_FEE,\n" + 
				"       E.TICKET_STAT_DID,\n" + 
				"       E.deal_pay_amount,\n" + 
				"       E.real_pay_amount,\n" + 
				"       E.CREATOR_ID,\n" + 
				"       E.CREATE_DATE,\n" + 
				"       E.LAST_UPD_USER_ID,\n" + 
				"       e.change_charge_fee,\n" + 
				"       P.PSGR_NAME,\n" + 
				"       P.PSGR_TYPE_DCD,\n" + 
				"       P.CERT_TYPE_DID,\n" + 
				"       P.CERT_NO,\n" + 
				"       P.TICKET_FARE as ticketFare_psg,\n" + 
				"       P.FUEL_FEE as fuelFee_psg,\n" + 
				"       P.AIRPORT_FEE as airportFee_psg,\n" + 
				"       P.PSGR_ID,\n" + 
				"       P.CONTACT_MOBILE,\n" + 
				"       P.EMAIL_ADDR,\n" + 
				"       P.CREATOR_ID,\n" + 
				"       P.CREATE_DATE,\n" + 
				"       P.LAST_UPD_USER_ID,\n" + 
				"       P.LAST_UPD_DATE,\n" + 
				"       S.AIR_COM_CODE,\n" + 
				"       S.SEQ_NUM,\n" + 
				"       S.FLIGHT_NO,\n" + 
				"       S.IS_SHARED,\n" + 
				"       S.PLANE_TYPE,\n" + 
				"       S.CABIN,\n" + 
				"       S.FROM_CITY_DCD,\n" + 
				"       S.TO_CITY_DCD,\n" + 
				"       S.FROM_DATE,\n" + 
				"       S.TO_DATE,\n" + 
				"       S.CREATOR_ID,\n" + 
				"       S.CREATE_DATE,\n" + 
				"       S.LAST_UPD_USER_ID,\n" + 
				"       S.LAST_UPD_DATE,\n" + 
				"       l.ticket_type_codes,\n" + 
				"       l.is_chg_pnr,\n" + 
				"       l.policy_id,\n" + 
				"       l.ticket_office_no,\n" + 
				"       sp.short_name,\n" + 
				"       sp.supplier_id,\n" + 
				"       sp.office_no,\n" + 
				"       r.full_name,\n" + 
				"       R.LOGIN_NAME,\n" + 
				"       R.CONTACT_TEL,\n" + 
				"       a.IS_HL_FINISHED\n" + 
				"  from od_order         a,\n" + 
				"       od_order_extra   e,\n" + 
				"       od_passenger     p,\n" + 
				"       pl_policy_saled  l,\n" + 
				"       od_segment       s,\n" + 
				"       sp_supplier      sp,\n" + 
				"       sp_supplier_user r\n" + 
				" where \n" + 
				" s.seg_id = e.seg_id\n" + 
				"   and a.order_no = p.order_no\n" + 
				"   and p.psgr_id = e.psgr_id\n" + 
				"   and e.ticket_policy_saled_id = l.saled_id(+)\n" + 
				"   and a.order_no = s.order_no\n" + 
				"   and a.supplier_id = sp.supplier_id\n" + 
				"   and a.last_upd_user_id = r.user_id(+)\n" + 
				"   and a.order_no in\n" + 
				"       (select o.order_no\n" + 
				"          from (select a.order_no, rownum rn\n" + 
				"                  from (select distinct a.order_no,\n" + 
				"                                        a.create_date,\n" + 
				"                                        a.ticket_date\n" + 
				"                          from od_order         a,\n" + 
				"                               od_order_extra   e,\n" + 
				"                               od_passenger     p,\n" + 
				"                               pl_policy_saled  l,\n" + 
				"                               od_segment       s,\n" + 
				"                              \n" + 
				"                               sp_supplier_user r\n" + 
				"                         where \n" + 
				"                          to_char(a.create_date,'yyyy-mm-dd')>='2015-12-01'\n" + 
				"                           and to_char(a.create_date,'yyyy-mm-dd')<='2015-12-07'\n" + 
				"                           and s.seg_id = e.seg_id\n" + 
				"                           and a.order_no = p.order_no\n" + 
				"                           and p.psgr_id = e.psgr_id\n" + 
				"                           and e.ticket_policy_saled_id = l.saled_id(+)                          \n" + 
				"                           and a.last_upd_user_id = r.user_id(+)                         \n" + 
				"                           and (a.ORDER_SRC_DID = 0 or a.ORDER_SRC_DID is null)\n" + 
				"                           and a.policy_type_did in (1)\n" + 
				"                            and a.order_type_did =1\n" + 
				"                            and a.ticket_auto_type_did in (3,2)\n" + 
				"                            and a.order_stat_did in (1, 2, 4, 12)\n" + 
				"                           and a.is_central_dept_time = 'Y'\n" + 
				"                         order by a.create_date asc) a) o\n" + 
				"         where o.rn > 0\n" + 
				"           and o.rn <= 10)\n" + 
				" order by s.SEQ_NUM, psgr_id asc\n" + 
				" \n" + 
				" \n" + 
				" \n";
				
		
		System.out.println(sqlString.toLowerCase());
	}

}
