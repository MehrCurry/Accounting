package de.gzockoll.accounting;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;

public class AuditLog {
	private static Collection<AuditLogEntry> log = new ArrayList<AuditLogEntry>();

	public static void add(String text, Subject subject) {
		log.add(new AuditLogEntry(text, subject));

	}

	public static String to_s() {
		StringBuffer s = new StringBuffer();
		for (AuditLogEntry e : log)
			s.append(e);
		return s.toString();
	}

	private static class AuditLogEntry {
		private String text;
		private Subject subject;
		private DateTime time;

		private AuditLogEntry(String text, Subject subject) {
			super();
			this.time = new DateTime();
			this.text = text;
			this.subject = subject;
		}

		@Override
		public String toString() {
			return time + "\t" + text + "\t" + subject;
		}

	}
}
