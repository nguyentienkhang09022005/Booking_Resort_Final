package com.example.Booking_Resort.mapper;

import com.example.Booking_Resort.dto.request.MonthlyReportCreationRequest;
import com.example.Booking_Resort.dto.request.MonthlyReportUpdateRequest;
import com.example.Booking_Resort.dto.response.DetailReportResponse;
import com.example.Booking_Resort.dto.response.MonthlyReportResponse;
import com.example.Booking_Resort.models.Detail_Report;
import com.example.Booking_Resort.models.Monthly_Report;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-20T10:57:17+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Oracle Corporation)"
)
@Component
public class MonthlyReportMapperImpl implements MonthlyReportMapper {

    @Override
    public Monthly_Report toMonthlyReport(MonthlyReportCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        Monthly_Report monthly_Report = new Monthly_Report();

        monthly_Report.setReportMonth( request.getReportMonth() );
        monthly_Report.setReportYear( request.getReportYear() );
        List<Detail_Report> list = request.getDetails();
        if ( list != null ) {
            monthly_Report.setDetails( new ArrayList<Detail_Report>( list ) );
        }

        return monthly_Report;
    }

    @Override
    public MonthlyReportResponse toMonthlyReportResponse(Monthly_Report report) {
        if ( report == null ) {
            return null;
        }

        MonthlyReportResponse.MonthlyReportResponseBuilder monthlyReportResponse = MonthlyReportResponse.builder();

        monthlyReportResponse.idReport( report.getIdReport() );
        monthlyReportResponse.reportMonth( report.getReportMonth() );
        monthlyReportResponse.reportYear( report.getReportYear() );
        monthlyReportResponse.totalRevenue( report.getTotalRevenue() );
        monthlyReportResponse.totalExpense( report.getTotalExpense() );
        monthlyReportResponse.netProfit( report.getNetProfit() );
        monthlyReportResponse.generatedAt( report.getGeneratedAt() );
        monthlyReportResponse.details( detail_ReportListToDetailReportResponseList( report.getDetails() ) );

        return monthlyReportResponse.build();
    }

    @Override
    public void updateMonthlyReport(Monthly_Report report, MonthlyReportUpdateRequest request) {
        if ( request == null ) {
            return;
        }

        report.setReportMonth( request.getReportMonth() );
        report.setReportYear( request.getReportYear() );
        if ( report.getDetails() != null ) {
            List<Detail_Report> list = request.getDetails();
            if ( list != null ) {
                report.getDetails().clear();
                report.getDetails().addAll( list );
            }
            else {
                report.setDetails( null );
            }
        }
        else {
            List<Detail_Report> list = request.getDetails();
            if ( list != null ) {
                report.setDetails( new ArrayList<Detail_Report>( list ) );
            }
        }
    }

    protected DetailReportResponse detail_ReportToDetailReportResponse(Detail_Report detail_Report) {
        if ( detail_Report == null ) {
            return null;
        }

        DetailReportResponse.DetailReportResponseBuilder detailReportResponse = DetailReportResponse.builder();

        detailReportResponse.type( detail_Report.getType() );
        detailReportResponse.amount( detail_Report.getAmount() );
        detailReportResponse.createDate( detail_Report.getCreateDate() );

        return detailReportResponse.build();
    }

    protected List<DetailReportResponse> detail_ReportListToDetailReportResponseList(List<Detail_Report> list) {
        if ( list == null ) {
            return null;
        }

        List<DetailReportResponse> list1 = new ArrayList<DetailReportResponse>( list.size() );
        for ( Detail_Report detail_Report : list ) {
            list1.add( detail_ReportToDetailReportResponse( detail_Report ) );
        }

        return list1;
    }
}
