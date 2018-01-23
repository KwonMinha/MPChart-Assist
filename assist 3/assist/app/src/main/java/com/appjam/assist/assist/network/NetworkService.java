package com.appjam.assist.assist.network;

import android.content.Context;

import com.appjam.assist.assist.model.request.Attend;
import com.appjam.assist.assist.model.request.GameReport;
import com.appjam.assist.assist.model.request.GameReportResult;
import com.appjam.assist.assist.model.request.Login;
import com.appjam.assist.assist.model.response.AttendListResult;
import com.appjam.assist.assist.model.response.Backnumber;
import com.appjam.assist.assist.model.response.Email;
import com.appjam.assist.assist.model.response.NomalLoginResult;
import com.appjam.assist.assist.model.response.PlayerMonthResult;
import com.appjam.assist.assist.model.response.PlayerResult;
import com.appjam.assist.assist.model.response.PositionResult;
import com.appjam.assist.assist.model.response.RankingResult;
import com.appjam.assist.assist.model.response.ResultCheck;
import com.appjam.assist.assist.model.response.ResultMessage;
import com.appjam.assist.assist.model.response.ScheduleResult;
import com.appjam.assist.assist.model.response.TacticMemberResult;
import com.appjam.assist.assist.model.response.TacticResult;
import com.appjam.assist.assist.model.response.TeamAffectResult;
import com.appjam.assist.assist.model.response.TeamId;
import com.appjam.assist.assist.model.response.TeamList;
import com.appjam.assist.assist.model.response.TeamListResult;
import com.appjam.assist.assist.model.response.TeamMemberResult;
import com.appjam.assist.assist.model.response.TeamMonthResult;
import com.appjam.assist.assist.model.response.TeamPlayResult;
import com.appjam.assist.assist.model.response.TeamProfile;
import com.appjam.assist.assist.model.response.TeamProfileResult;
import com.appjam.assist.assist.model.response.TeamRankingResult;
import com.appjam.assist.assist.team.Schedule;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by gominju on 2017. 6. 28..
 */

public interface NetworkService {
    // 로그인
    @POST("/login")
    Call<NomalLoginResult> getLoginResult(@Body Login login);

    // 회원가입
    @Multipart
    @POST("/signup")
    Call<ResultMessage> joinMember(@PartMap() Map<String, RequestBody> memberData, @Part MultipartBody.Part profile_pic);

    // 회원가입 이메일 중복 확인
    @POST("/signup/dupcheck")
    Call<ResultCheck> checkEmail(@Body Email email);

    // 특정 팀 멤머 중복 확인
    @POST("/team/{team_id}/members/dupcheck")
    Call<ResultCheck> checkBacknumber(@Path("team_id") int team_id, @Body Backnumber backnumber);


    // 팀 목록 조회
    @GET("/team")
    Call<TeamListResult> getTeamList();

    // 특정 팀 프로필 조회
    @GET("/team/{team_id}")
    Call<TeamProfileResult> getTeamProfile(@Path("team_id") int team_id);

    // 팀 창단
    @Multipart
    @POST("/team")
    Call<TeamId> makeTeam(@PartMap() Map<String, RequestBody> teamData, @Part MultipartBody.Part profile_pic);

    // 팀 메인 -> 특정 팀 프로필 조회

    // 월별 일정 조회
    @GET("/team/{team_id}/schedule/year/{year}/month/{month}")
    Call<ScheduleResult> getMonthSchedule(@Path("team_id") int team_id, @Path("year") int year, @Path("month") int month);

    // 참석, 불참석 클릭
    @POST("/schedule/{schedule_id}/attendee")
    Call<Void> setAttend(@Path("schedule_id") int schedule_id, @Body Attend attend);

    // 참석, 불참석 목록 조회
    @GET("/schedule/{schedule_id}/attendee")
    Call<AttendListResult> getAttendList(@Path("schedule_id") int schedule_id);

    // 일정 추가
    @POST("/team/{team_id}/schedule")
    Call<ResultMessage> addSchedule(@Path("team_id") int team_id, @Body Schedule schedule);

    // 팀 멤버 목록 조회
    @GET("/team/{team_id}/members")
    Call<TeamMemberResult> getTeamMemberList(@Path("team_id") int team_id);

    // 특정 팀 최근 경기
    @GET("/team/{team_id}/schedule")
    Call<TeamPlayResult> getTeamPlayList(@Path("team_id") int team_id);

    // 팀 월별 기록
    @GET("/team/{team_id}/report/month")
    Call<TeamMonthResult> getMonthList(@Path("team_id") int team_id);

    // 선수 월별 기록
    @GET("/player/{player_id}/report/month")
    Call<PlayerMonthResult> getPlayerMonthList(@Path("player_id") int player_id);

    // 전술 분석
    @GET("/team/{team_id}/report/tactic")
    Call<TacticResult> getTacticList(@Path("team_id") int team_id);

    // 포지션 별
    @GET("player/{player_id}/report/affect/position")
    Call<PositionResult> getPositionList(@Path("player_id") int player_id);

    // 개인 기록 조회
    @GET("/player/{player_id}")
    Call<PlayerResult> getPlayerInfo(@Path("player_id") int player_id);

    // 팀 순위
    @GET("/team/{team_id}/members/sort")
    Call<RankingResult> getRankList(@Path("team_id") int team_id);

    // 개인기록 - 팀 관여율
    @GET("/player/{player_id}/report/affect/team")
    Call<TeamAffectResult> getAffectList(@Path("player_id") int player_id);

    // 특정 전술에 따른 선수 목록
    @GET("/schedule/{schedule_id}/attendee/tactic/{tactic}")
    Call<TacticMemberResult> getTacticMembers(@Path("schedule_id") int schedule_id, @Path("tactic") String tactic);

    // 게임 기록 전송
    @POST("/schedule/{schedule_id}/report")
    Call<ResultMessage> setGameReport(@Path("schedule_id") int schedule_id, @Body GameReport gameReport);

    // 게임 기록 조회
    @GET("/schedule/{schedule_id}/report")
    Call<GameReportResult> getGameReport(@Path("schedule_id") int schedule_id);

    // 마이페이지 수정
    @Multipart
    @POST("/player/{player_id}")
    Call<ResultMessage> modifiedMypage(@Path("player_id") int player_id, @PartMap() Map<String, RequestBody> mypageData, @Part MultipartBody.Part profile_pic);

    // 팀 랭킹 목록
    @GET("/team/rank")
    Call<TeamRankingResult> getRankingList();

}
