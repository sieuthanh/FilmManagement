package statistics;

import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import admin.DAO.HibernateUtil;
import bill.BillArchive;

public class StatisticUtil {
	public ArrayList<Integer> getStatisticEachDayInMonth(int month, int year) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		int sum = 0;
		HibernateUtil hu = new HibernateUtil();
		SessionFactory sf = hu.getSessionFactory();
		Session session = sf.openSession();
		Criteria criteria;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		int numberOfDaysInCurrentMonth = calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < numberOfDaysInCurrentMonth; i++) {
			criteria = session.createCriteria(BillArchive.class);
			criteria.add(Restrictions.eq("month", month))
					.add(Restrictions.eq("year", year))
					.add(Restrictions.eq("day", i + 1));
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.sum("total"));
			criteria.setProjection(projectionList);
			if (criteria.uniqueResult() != null) {
				sum = Integer.parseInt(criteria.uniqueResult() + "");
			} else {
				sum = 0;
			}
			results.add(sum);
		}
		session.close();
		return results;
	}

	public int saveStatisticsEachDayInMonth(
			ArrayList<Integer> eachDayInMonthStatisticResult, int month,
			int year) {
		DayStatistics ds;
		HibernateUtil hu = new HibernateUtil();
		SessionFactory sf = hu.getSessionFactory();
		Session session = sf.openSession();
		int i = 0;
		int MonthSumMoney = 0;
		for (i = 0; i < eachDayInMonthStatisticResult.size(); i++) {
			ds = new DayStatistics();
			ds.setDay(i + 1);
			ds.setMonth(month);
			ds.setYear(year);
			ds.setSum(eachDayInMonthStatisticResult.get(i));
			session.merge(ds);
			MonthSumMoney = MonthSumMoney
					+ eachDayInMonthStatisticResult.get(i);
		}
		MonthStatistics ms = new MonthStatistics();
		ms.setSum(MonthSumMoney);
		ms.setYear(year);
		ms.setMonth(month);
		session.merge(ms);
		session.beginTransaction().commit();
		session.close();
		return i;
	}

	public ArrayList<Integer> getStatisticsEachMonthInYear(int year) {
		ArrayList<Integer> results = new ArrayList<Integer>();
		HibernateUtil hu = new HibernateUtil();
		SessionFactory sf = hu.getSessionFactory();
		Session session = sf.openSession();
		MonthStatistics ms = new MonthStatistics();
		Criteria criteria;
		int sum = 0;
		for (int i = 0; i < 12; i++) {
			criteria = session.createCriteria(MonthStatistics.class);
			criteria.add(Restrictions.eq("month", (i + 1))).add(
					Restrictions.eq("year", year));
			ms = (MonthStatistics) criteria.uniqueResult();

			if (ms != null) {
				sum = ms.getSum();
				results.add(sum);
			} else {
				results.add(0);
			}
		}
		return results;
	}

	public static void main(String[] args) {
		StatisticUtil su = new StatisticUtil();
		ArrayList<Integer> results = new ArrayList<Integer>();
		results = su.getStatisticsEachMonthInYear(2012);

		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i));
		}

	}
}
