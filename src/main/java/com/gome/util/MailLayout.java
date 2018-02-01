package com.gome.util;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class MailLayout extends Layout {
	StringBuffer sbuf;

	@Override
	public String getContentType() {
		return "text/html;charset=utf-8";
	}

	public MailLayout() {
		sbuf = new StringBuffer(128);
	}

	@Override
	public String format(LoggingEvent event) {
		sbuf.setLength(0);
		sbuf.append("错误等级：" + event.getLevel().toString() + "<br>");
		sbuf.append("错误的类：" + event.getLocationInformation().getClassName() + "<br>");
		sbuf.append("错误方法：" + event.getLocationInformation().getMethodName() + "<br>");
		sbuf.append("错误位置：" + event.getLocationInformation().getLineNumber() + "行" + "<br>");
		if( event.getThrowableInformation() == null ){
			sbuf.append("错误原因：" + event.getMessage().toString()  );
		}else{
			sbuf.append("错误原因：" + getExceptionDetail(event.getThrowableInformation().getThrowable()) );
		}

		return sbuf.toString();
	}

	@Override
	public boolean ignoresThrowable() {
		return false;
	}

	public void activateOptions() {
	}

	/**
	 * 获取exception详情信息
	 * 
	 * @param e
	 *            Excetipn type
	 * @return String type
	 */
	public static String getExceptionDetail(Throwable e) {
		StringBuffer msg = new StringBuffer();
		if (e != null) {
			msg = new StringBuffer("");
			String message = e.toString();
			int length = e.getStackTrace().length;
			if (length > 0) {
				msg.append(message + "\n");
				for (int i = 0; i < length; i++) {
					msg.append("\t" + e.getStackTrace()[i] + "\n");
				}
			} else {

				msg.append(message);
			}
		}
		return msg.toString();

	}

}
