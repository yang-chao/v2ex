package com.price.v2ex.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.price.v2ex.io.model.Member;
import com.price.v2ex.provider.V2exContract.Members;

import java.util.ArrayList;

/**
 * Created by YC on 15-1-15.
 */
public class MembersHandler extends JSONHandler<Member> {

    public MembersHandler(Context context) {
        super(context);
    }

    @Override
    public void makeContentProviderOperations(ArrayList<ContentProviderOperation> list) {
        final Uri uri = Members.CONTENT_URI;
//        list.add(ContentProviderOperation.newDelete(uri).build());

        for (Member member : mData) {
            list.add(makeInsertOperation(uri, member));
        }
    }

    @Override
    public void process(JsonElement element) {
        Member[] members = new Gson().fromJson(element, Member[].class);
        for (Member member : members) {
            mData.add(member);
        }
    }

    public ContentProviderOperation makeInsertOperation(Uri uri, Member member) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(uri);
        builder.withValue(Members.MEMBER_ID, member.getId());
        builder.withValue(Members.MEMBER_USERNAME, member.getUsername());
        builder.withValue(Members.MEMBER_TAGLINE, member.getTagline());
        builder.withValue(Members.MEMBER_AVATAR_MINI, member.getAvatarMini());
        builder.withValue(Members.MEMBER_AVATAR_NORMAL, member.getAvatarNormal());
        builder.withValue(Members.MEMBER_AVATAR_LARGE, member.getAvatarLarge());
        return builder.build();
    }
}
