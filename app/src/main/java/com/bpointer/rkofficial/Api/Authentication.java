package com.bpointer.rkofficial.Api;

import com.bpointer.rkofficial.Model.RequestBody;
import com.bpointer.rkofficial.Model.Response.AddFundResponseModel.AddFundResponseModel;
import com.bpointer.rkofficial.Model.Response.AddressDetailsResponseModel.AddressDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.AdminDetailsResponseModel.AdminDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.BankDetailsResponseModel.BankDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.BidHistoryReponseModel.BidHistoryResponseModel;
import com.bpointer.rkofficial.Model.Response.DepositHistoryResponseModel.DepositHistoryResponseModel;
import com.bpointer.rkofficial.Model.Response.GPayResponseModel.GPayResponseModel;
import com.bpointer.rkofficial.Model.Response.GameRatingResponseModel.GameRatingResponseModel;
import com.bpointer.rkofficial.Model.Response.GetActiveUpiModel;
import com.bpointer.rkofficial.Model.Response.GetAppVersionResponse.GetAppVersionResponse;
import com.bpointer.rkofficial.Model.Response.GetBankDetailsResponseModel.GetBankDetailsResponseModel;
import com.bpointer.rkofficial.Model.Response.GetGamesResponseModel.GetGamesResponseModel;
import com.bpointer.rkofficial.Model.Response.GetJodiDigitResponseModel.GetJodiDigitResponse;
import com.bpointer.rkofficial.Model.Response.GetSingleDigitResponseModel.GetSingleDigitResponse;
import com.bpointer.rkofficial.Model.Response.HomeResponseModel.HomeResponseModel;
import com.bpointer.rkofficial.Model.Response.LoginResponseModel.LoginResponseModel;
import com.bpointer.rkofficial.Model.Response.MPINLoginResponseModel.MPINLoginResponseModel;
import com.bpointer.rkofficial.Model.Response.MpinGenerateResponseModel;
import com.bpointer.rkofficial.Model.Response.NotificationResponseModel.NotificationResponseModel;
import com.bpointer.rkofficial.Model.Response.PaymentTransactionResponseModel.PaymentTransactionResponseModel;
import com.bpointer.rkofficial.Model.Response.PaytmResponseModel.PaytmResponseModel;
import com.bpointer.rkofficial.Model.Response.PhonePeResponseModel.PhonePeResponseModel;
import com.bpointer.rkofficial.Model.Response.PlayGameResponseModel.PlayGameResponseModel;
import com.bpointer.rkofficial.Model.Response.PostTokenResponseModel.PostTokenResponseModel;
import com.bpointer.rkofficial.Model.Response.RegisterResponseModel.RegisterResponseModel;
import com.bpointer.rkofficial.Model.Response.SendOtpResponseModel.SendOtpResponseModel;
import com.bpointer.rkofficial.Model.Response.StarLineCompanyResponseModel.StarLineCompanyResponseModel;
import com.bpointer.rkofficial.Model.Response.StarLineGameResponseModel.StarLineGameResponseModel;
import com.bpointer.rkofficial.Model.Response.StarLineMarketTimeResponseModel.StarLineMarketTimeResponseModel;
import com.bpointer.rkofficial.Model.Response.UpdatePasswordResponseModel.UpdatePasswordResponseModel;
import com.bpointer.rkofficial.Model.Response.VerifyNumberResponseModel.VerifyNumberResponseModel;
import com.bpointer.rkofficial.Model.Response.WinHistoryResponseModel.WinHistoryResponseModel;
import com.bpointer.rkofficial.Model.Response.WithdrawAmountResponseModel.WithdrawAmountResponseModel;
import com.bpointer.rkofficial.Model.Response.WithdrawHistoryResponseModel.WithdrawHistoryResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Authentication {

    @POST("postUserRegistration")
    Call<RegisterResponseModel> register(@Body RequestBody body);

    @POST("postUserLogin")
    Call<LoginResponseModel> login(@Body RequestBody body);

    @POST("postMpinLogin")
    Call<MPINLoginResponseModel> mpinLogin(@Body RequestBody body);

    @POST("postHome")
    Call<HomeResponseModel> getHomeData(@Body RequestBody body);

    @GET("gameRating")
    Call<GameRatingResponseModel> getGameRating();

    @POST("mpinGenerate")
    Call<MpinGenerateResponseModel> generateMpin(@Body RequestBody body);

    @POST("getSingleDigitDetails")
    Call<GetSingleDigitResponse> getSingleDigitDetails(@Body RequestBody body);

    @POST("getJodiDigitDetails")
    Call<GetJodiDigitResponse> getJodiDigitDetails(@Body RequestBody body);

    @GET("getGame")
    Call<GetGamesResponseModel> getGames();

    @POST("playGame")
    Call<PlayGameResponseModel> playGame(@Body RequestBody body);

    @GET("getAdminDetails")
    Call<AdminDetailsResponseModel> getAdminDetails();

    @POST("addUserDetails")
    Call<AddressDetailsResponseModel> updateAddressDetails(@Body RequestBody body);

    @POST("addBankDetails")
    Call<BankDetailsResponseModel> updateBankDetails(@Body RequestBody body);

    @POST("addGooglepayDetails")
    Call<GPayResponseModel> updateGPayDetails(@Body RequestBody body);

    @POST("addPaytmDetails")
    Call<PaytmResponseModel> updatePayTmDetails(@Body RequestBody body);

    @POST("addPhonePayDetails")
    Call<PhonePeResponseModel> updatePhonePeDetails(@Body RequestBody body);

    @POST("withdrawAmount")
    Call<WithdrawAmountResponseModel> withdrawAmount(@Body RequestBody body);

    @POST("addWalletRequest")
    Call<AddFundResponseModel> addFund(@Body RequestBody body);

    @POST("gameList")
    Call<BidHistoryResponseModel> getBidHistory(@Body RequestBody body);

    @POST("withdrawHistory")
    Call<WithdrawHistoryResponseModel> getWithdrawHistory(@Body RequestBody body);

    @POST("depositHistory")
    Call<DepositHistoryResponseModel> getDepositHistory(@Body RequestBody body);

    @POST("winnerHistory")
    Call<WinHistoryResponseModel> getWinHistory(@Body RequestBody body);

    @POST("sendOtp")
    Call<SendOtpResponseModel> sendOtp(@Body RequestBody body);

    @POST("updatePassword")
    Call<UpdatePasswordResponseModel> updatePassword(@Body RequestBody body);

    @GET("getStarlineCompany")
    Call<StarLineCompanyResponseModel> getStarlineCompany();

    @POST("postStarlineTime")
    Call<StarLineMarketTimeResponseModel> getStarLineMarketTime(@Body RequestBody body);

    @GET("getStarLineGame")
    Call<StarLineGameResponseModel> getStarLineGames();

    @POST("postToken")
    Call<PostTokenResponseModel> postToken(@Body RequestBody body);

    @GET("getNotification")
    Call<NotificationResponseModel> getNotification();

    @POST("getBankDetails")
    Call<GetBankDetailsResponseModel> getBankDetails(@Body RequestBody body);

    @POST("verifyNumber")
    Call<VerifyNumberResponseModel> verifyNumber(@Body RequestBody body);

    @POST("postPaymentTransaction")
    Call<PaymentTransactionResponseModel> postPaymentTransaction(@Body RequestBody body);
    
    @GET("getAppVersion")
    Call<GetAppVersionResponse> getAppVersion();

    @POST("postPaymentOptionList")
    Call<GetActiveUpiModel> getActiveUpiId();
}
