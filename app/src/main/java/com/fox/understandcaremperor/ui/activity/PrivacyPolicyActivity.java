package com.fox.understandcaremperor.ui.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.fox.understandcaremperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/app/PrivacyPolicyActivity")
public class PrivacyPolicyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_privacy_policy)
    TextView tvPrivacyPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String text = "1.我们如何收集和使用您的个人信息\n" +
                "\n" +
                "(a) 在您注册本应用帐号时，您根据本应用要求提供的个人注册信息；\n" +
                "\n" +
                "(b) 在您使用本应用网络服务，或访问本应用平台网页时， 本应用自动接收并记录的您的浏览器和计算机上的信息， 包括但不限于您的IP地址、浏览器的类型、使用的语言、 访问日期和时间、软硬件特征信息及您需求的网页记录等 数据；\n" +
                "\n" +
                "(c) 本应用通过合法途径从商业伙伴处取得的用户个人数 据。\n" +
                "\n" +
                "您了解并同意，以下信息不适用本隐私权政策：\n" +
                "\n" +
                "(a) 您在使用本应用平台提供的搜索服务时输入的关键字 信息；\n" +
                "\n" +
                "(b) 本应用收集到的您在本应用发布的有关信息数据，包 括但不限于参与活动、成交信息及评价详情；\n" +
                "\n" +
                "(c) 违反法律规定或违反本应用规则行为及本应用已对您 采取的措施。\n" +
                "\n" +
                "2.我们如何保护您的个人信息安全\n" +
                "\n" +
                "(a)本应用不会向任何无关第三方提供、出售、出租、分享 或交易您的个人信息，除非事先得到您的许可，或该第三 方和本应用（含本应用关联公司）单独或共同为您提供服 务，且在该服务结束后，其将被禁止访问包括其以前能够 访问的所有这些资料。\n" +
                "\n" +
                "(b) 本应用亦不允许任何第三方以任何手段收集、编辑、 出售或者无偿传播您的个人信息。任何本应用平台用户如 从事上述活动，一经发现，本应用有权立即终止与该用户 的服务协议。\n" +
                "\n" +
                "(c) 为服务用户的目的，本应用可能通过使用您的个人信息 ，向您提供您感兴趣的信息，包括但不限于向您发出产品 和服务信息，或者与本应用合作伙伴共享信息以便他们向 您发送有关其产品和服务的信息（后者需要您的事先同意）\n" +
                "\n" +
                "2.1信息存储和交换\n" +
                "\n" +
                "本应用收集的有关您的信息和资料将保存在本应用及（或） 其关联公司的服务器上，这些信息和资料可能传送至您所 在国家、地区或本应用收集信息和资料所在地的境外并在 境外被访问、存储和展示。\n" +
                "\n" +
                "3.我们如何转让、共享、公开您的个人信息\n" +
                "\n" +
                "在如下情况下，本应用将依据您的个人意愿或法律的规定 全部或部分的披露您的个人信息：\n" +
                "\n" +
                "(a) 经您事先同意，向第三方披露；\n" +
                "\n" +
                "(b)为提供您所要求的产品和服务，而必须和第三方分享您 的个人信息；\n" +
                "\n" +
                "(c) 根据法律的有关规定，或者行政或司法机构的要求，向 第三方或者行政、司法机构披露；\n" +
                "\n" +
                "(d) 如您出现违反中国有关法律、法规或者本应用服务协议 或相关规则的情况，需要向第三方披露；\n" +
                "\n" +
                "(e) 如您是适格的知识产权投诉人并已提起投诉，应被投诉 人要求，向被投诉人披露，以便双方处理可能的权利纠纷；\n" +
                "\n" +
                "(f) 在本应用平台上创建的某一交易中，如交易任何一方履 行或部分履行了交易义务并提出信息披露请求的，本应用 有权决定向该用户提供其交易对方的联络方式等必要信息， 以促成交易的完成或纠纷的解决。\n" +
                "\n" +
                "(g) 其它本应用根据法律、法规或者网站政策认为合适的披露。\n" +
                "\n" +
                "4.我们如何使用Cookie和同类技术\n" +
                "\n" +
                "(a) 在您未拒绝接受cookies的情况下，本应用会在您的计算 机上设定或取用cookies ，以便您能登录或使用依赖于cook ies的本应用平台服务或功能。本应用使用cookies可为您提 供更加周到的个性化服务，包括推广服务。\n" +
                "\n" +
                "(b) 您有权选择接受或拒绝接受cookies。您可以通过修改浏 览器设置的方式拒绝接受cookies。但如果您选择拒绝接受 cookies，则您可能无法登录或使用依赖于cookies的本应用 网络服务或功能。\n" +
                "\n" +
                "(c) 通过本应用所设cookies所取得的有关信息，将适用本政 策。\n" +
                "\n" +
                "5.您如何管理个人信息\n" +
                "\n" +
                "(a) 本应用帐号均有安全保护功能，请妥善保管您的用户名 及密码信息。本应用将通过对用户密码进行加密等安全措施 确保您的信息不丢失，不被滥用和变造。尽管有前述安全措 施，但同时也请您注意在信息网络上不存在“完善的安全措 施”。\n" +
                "\n" +
                "(b) 在使用本应用网络服务进行网上交易时，您不可避免的 要向交易对方或潜在的交易对\n" +
                "\n" +
                "6.本隐私政策的更改\n" +
                "\n" +
                "(a)如果决定更改隐私政策，我们会在本政策中、本公司网站 中以及我们认为适当的位置发布这些更改，以便您了解我们 如何收集、使用您的个人信息，哪些人可以访问这些信息， 以及在什么情况下我们会透露这些信息。\n" +
                "\n" +
                "(b)本公司保留随时修改本政策的权利，因此请经常查看。\n";
        tvPrivacyPolicy.setText(text);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//设置菜单栏响应事件
        switch (item.getItemId()) {
            case android.R.id.home://home按钮
                finish();
                break;
        }
        return true;
    }



}