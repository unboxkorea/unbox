package com.example.bang.toeichelper.myfragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bang.toeichelper.LoginedActivity;
import com.example.bang.toeichelper.R;
import com.example.bang.toeichelper.RSA;
import com.example.bang.toeichelper.customdlg.ColorProgressDlg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by BANG on 2015-01-10.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private EditText edtxID;
    private EditText edtxPW;

    private Button btnJoin;
    private Button btnLogin;

    private String strEmail;
    private String strPW;

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;


    public static LoginFragment create(){
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //자동 로그인 검사
        preferences = getActivity().getSharedPreferences("pref", getActivity().MODE_PRIVATE);
        editor = preferences.edit();
        String preID = preferences.getString("ID", "NOT");
        String prePW = preferences.getString("PW", "NOT");

        Log.w("preID", " = " + preID);
        if(!preID.equals("NOT")){
//            Intent intent = new Intent(MainActivity.this, LoginedActivity.class);
//            startActivity(intent);
//            Log.w("Auto Logined", "OK");
            strEmail = preID;
            strPW = prePW;
            RequestLogin requestLogin = new RequestLogin();
            requestLogin.execute("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);

        edtxID = (EditText) rootView.findViewById(R.id.edtxLoginID);
        edtxPW = (EditText) rootView.findViewById(R.id.edtxLoginPW);

        btnJoin = (Button) rootView.findViewById(R.id.btnLoginJoin);
        btnLogin = (Button) rootView.findViewById(R.id.btnLoginLogin);

        btnJoin.setOnClickListener(LoginFragment.this);
        btnLogin.setOnClickListener(LoginFragment.this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnJoin.getId()){
            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.vpagerMain);
            viewPager.setCurrentItem(1);
        }
        else if(v.getId() == btnLogin.getId()){
            //Toast.makeText(getActivity(), "로그인", Toast.LENGTH_SHORT).show();
            strEmail = edtxID.getText().toString();
            strPW = edtxPW.getText().toString();

            //빈칸 검사 && 띄어쓰기 검사
            if( IsEmpty(strEmail) || strSpaceCheck(strEmail)
                    || IsEmpty(strPW) || strSpaceCheck(strPW) ){
                Toast.makeText(getActivity(), "입력란을 비우거나 공백을 포함할 수 없습니다.", Toast.LENGTH_SHORT).show();

            }
            else{
                //LoginCheck
                RequestLogin requestLogin = new RequestLogin();
                requestLogin.execute("");
            }
        }
        //Log.w("Login onClick", "ok = " + v.getId());
    }

    public boolean IsEmpty(String str){
        return str.equals("") || ( str.length() == 0 ) ;
    }

    public boolean strSpaceCheck(String str){
        for(int i=0;i<str.length();i++){
            if(str.charAt(i) == ' '){
                return true;
            }
        }
        return false;
    }

    public EditText getEdtxID() {
        return edtxID;
    }

    public EditText getEdtxPW() {
        return edtxPW;
    }

    public class RequestLogin extends AsyncTask<String, Integer, String> {

        private String strURL = "http://kebin1104.dothome.co.kr/ToeicHelper/LoginMember.php";

        private String eEmail;
        private String ePW;

        private String ePk;
        private String eName;

        private String strRsaIndex;
        private String strPk;
        private String strName;

        private RSA rsa;
        private int rsaIndex;

        private ColorProgressDlg dlg;

        public RequestLogin() {
            rsa = new RSA();

            Random random = new Random();
            rsaIndex = random.nextInt(4) + 1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dlg = new ColorProgressDlg(getActivity(), "로그인 중...");
            dlg.setCancelable(false);
            dlg.show();

            eEmail = rsa.encryptMSG(strEmail, rsaIndex);
            ePW = rsa.encryptMSG(strPW, rsaIndex);
        }

        @Override
        protected String doInBackground(String... params) {
            String strResponse = "";

            try {
                //URL 설정
                URL url = new URL(strURL);

                //접속
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                if(http != null){	//연결이 제대로 되었다면

                    //전송 모드 설정
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    http.setRequestMethod("POST");	//전송방식을 POST

                    //서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
                    http.setRequestProperty("Accept", "application/xml");

                    //--------------------------
                    //  서버로 값 전송
                    //--------------------------

                    StringBuffer buffer = new StringBuffer();

                    buffer.append("RSA_index=").append(rsaIndex + "&");
                    buffer.append("member_Email=").append(eEmail + "&");
                    buffer.append("member_PW=").append(ePW);

                    OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
                    PrintWriter writer = new PrintWriter(outStream);
                    writer.write(buffer.toString());
                    writer.flush();

                    //--------------------------
                    //  서버에서 값 전송 받기
                    //--------------------------

                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(tmp);

                    strResponse = br.readLine();
                    if(strResponse.equals("success")){
                        strRsaIndex = br.readLine();
                        ePk = br.readLine();
                        eName = br.readLine();

                        rsaIndex = Integer.parseInt(strRsaIndex);
                    }

                    br.close();
                }

                http.disconnect();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return strResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.equals("email fail")){
                Toast.makeText(getActivity(), "이메일 주소를 다시 확인해 주세요!!", Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("pw fail")){
                Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다!!", Toast.LENGTH_SHORT).show();
            }
            else{

                strName = rsa.decryptMSG(eName, rsaIndex);
                strPk = rsa.decryptMSG(ePk, rsaIndex);

                Toast.makeText(getActivity(), strName + " 님 환영하오", Toast.LENGTH_SHORT).show();

                //자동 로그인 등록
                editor.putString("ID", strEmail);
                editor.putString("PW", strPW);
                editor.apply();
                Log.w("editor", strEmail + strPW);



                Intent intent = new Intent(getActivity(), LoginedActivity.class);
                startActivity(intent);
            }

            dlg.dismiss();
        }
    }
}
