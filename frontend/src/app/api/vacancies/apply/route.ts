import { Application, Vacancy } from '@/types';
import { NextResponse } from 'next/server';
import nodemailer from 'nodemailer';

interface mailProps {
  application: Application;
  vacancy: Vacancy;
}

export async function sendMail({ application, vacancy }: mailProps) {
  const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
      user: process.env.NODEMAILER_EMAIL,
      pass: process.env.NODEMAILER_PW,
    },
  });

  const emailHtml = `
    <h1>Olá, ${vacancy.ong.name}!</h1>
    <p>Uma nova aplicação foi feita para a vaga de <strong>${vacancy.title}</strong>.</p>
    <p>Nome: ${application.name}</p>
    <p>Email: ${application.email}</p>
    <p>Mensagem: ${application.message}</p>
    `;

  const options = {
    from: `sONGs <${process.env.NODEMAILER_EMAIL}>`,
    to: vacancy.ong.email,
    subject: 'Nova aplicação para vaga',
    html: emailHtml,
  };

  await transporter.sendMail(options);
}

export async function POST(request: Request) {
  const data = await request.json();

  await sendMail(data);

  return NextResponse.json({
    status: 200,
  });
}
