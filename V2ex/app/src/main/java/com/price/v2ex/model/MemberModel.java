package com.price.v2ex.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.price.v2ex.adapter.AdapterHandler;
import com.price.v2ex.io.model.Member;
import com.price.v2ex.provider.V2exContract.Members;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YC on 15-1-15.
 */
public class MemberModel extends DBModel<Member> {

    public MemberModel(Context context) {
        super(context);
    }

    @Override
    public List<Member> getListData() {
        return getMembers(mContext);
    }

    private static List<Member> getMembers(Context context) {
        Uri uri = Members.CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            ArrayList<Member> members = new ArrayList<Member>();
            while (cursor.moveToNext()) {
                members.add(cursorToMember(cursor));
            }
            cursor.close();
            return members;
        }
        return null;
    }

    public static Member getMember(Context context, String memberId) {
        Uri uri = Members.CONTENT_URI;
        String selection = Members.MEMBER_ID + "=?";
        String[] selectionArgs = {memberId};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Member member = cursorToMember(cursor);
                cursor.close();
                return member;
            }
            cursor.close();
        }
        return null;
    }

    private static Member cursorToMember(Cursor cursor) {
        Member member = new Member();
        member.id = cursor.getString(cursor.getColumnIndex(Members.MEMBER_ID));
        member.username = cursor.getString(cursor.getColumnIndex(Members.MEMBER_USERNAME));
        member.tagline = cursor.getString(cursor.getColumnIndex(Members.MEMBER_TAGLINE));
        member.avatarMini = cursor.getString(cursor.getColumnIndex(Members.MEMBER_AVATAR_MINI));
        member.avatarNormal = cursor.getString(cursor.getColumnIndex(Members.MEMBER_AVATAR_NORMAL));
        member.avatarLarge = cursor.getString(cursor.getColumnIndex(Members.MEMBER_AVATAR_LARGE));
        return member;
    }

    public static void updateMembers(final Context context, final RecyclerView.Adapter adapter) {
        new AsyncTask<Void, Void, List<Member>>() {
            @Override
            protected List<Member> doInBackground(Void... params) {
                return getMembers(context);
            }

            @Override
            protected void onPostExecute(List<Member> members) {
                AdapterHandler.notifyDataSetChanged(adapter, members);
            }
        }.execute((Void) null);
    }
}
